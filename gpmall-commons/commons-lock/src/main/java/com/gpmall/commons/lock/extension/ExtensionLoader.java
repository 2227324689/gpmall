package com.gpmall.commons.lock.extension;

import com.gpmall.commons.lock.extension.annotation.LockSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class ExtensionLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    private static final String LOCK_DIRECTORY = "META-INF/lock/";

    private static final String LOCK_INTERNAL_DIRECTORY = LOCK_DIRECTORY + "internal/";

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<Map<String, Class<?>>>();

    private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<Class<?>, String>();

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<Class<?>, Object>();

    private static final Map<Class, ExtensionLoader> extension_load_map = new ConcurrentHashMap<>(16);

    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<String, Holder<Object>>();

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    /**
     * wrapper
     */
    private Set<Class<?>> cachedWrapperClasses;

    /**
     * 默认扩展点
     * loadExtensionClasses 加载扩展文件的时候赋值
     */
    private String cachedDefaultName;

    private final Class<?> type;

    public ExtensionLoader(Class<?> type) {
        this.type = type;
        loadExtensionClasses();
    }

    public static ExtensionLoader getExtensionLoader(Class t) {
        //判断class
        check(t);
        ExtensionLoader extensionLoader = extension_load_map.get(t);
        if (extensionLoader != null) {
            return extensionLoader;
        }
        //此时创建Extension
        synchronized (extension_load_map) {
            extensionLoader = extension_load_map.get(t);
            if (extensionLoader == null) {
                extensionLoader = new ExtensionLoader(t);
                extension_load_map.put(t, extensionLoader);
            }
        }
        return extensionLoader;
    }


    public T getExtension(String name) {
        if (name == null || name.length() == 0 || "true".equals(name)) {
            return getDefaultExtension();
        }
        if (cachedWrapperClasses.contains(name)) {
            throw new IllegalStateException("name is error wrapper  is forbbiden");
        }
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<Object>());
            holder = cachedInstances.get(name);
        }
        Object instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    @SuppressWarnings("unchecked")
    private T createExtension(String name) {
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new IllegalStateException();
        }
        try {
            T instance = (T) EXTENSION_INSTANCES.get(clazz);
            if (instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(clazz, (T) clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            }
            Set<Class<?>> wrapperClasses = cachedWrapperClasses;
            if (wrapperClasses != null && wrapperClasses.size() > 0) {
                for (Class<?> wrapperClass : wrapperClasses) {
                    instance = (T) wrapperClass.getConstructor(type).newInstance(instance);
                }
            }
            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException("Extension instance(name: " + name + ", class: " +
                    type + ")  could not be instantiated: " + t.getMessage(), t);
        }
    }


    public T getDefaultExtension() {
        getExtensionClasses();
        if (null == cachedDefaultName || cachedDefaultName.length() == 0
                || "true".equals(cachedDefaultName)) {
            return null;
        }
        return getExtension(cachedDefaultName);
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    try {
                        classes = loadExtensionClasses();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }


    private Map<String, Class<?>> loadExtensionClasses() {
        //type 就是 DistributedLock
        final LockSpi defaultAnnotation = type.getAnnotation(LockSpi.class);
        if (defaultAnnotation != null) {
            String value = defaultAnnotation.value();
            if (value != null && (value = value.trim()).length() > 0) {
                String[] names = NAME_SEPARATOR.split(value);
                if (names.length > 1) {
                    throw new IllegalStateException("more than 1 default extension name on extension " + type.getName()
                            + ": " + Arrays.toString(names));
                }
                // cachedDefaultName = @LockSpi 的value
                if (names.length == 1) cachedDefaultName = names[0];
            }
        }

        Map<String, Class<?>> extensionClasses = new HashMap<String, Class<?>>();
        try {
            loadFile(extensionClasses, LOCK_DIRECTORY);
            loadFile(extensionClasses, LOCK_INTERNAL_DIRECTORY);
            loadFile(extensionClasses, SERVICES_DIRECTORY);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return extensionClasses;
    }

    private void loadFile(Map<String, Class<?>> extensionClasses, String dir) throws IOException {
        //这里拼凑了一个需要加载的文件路径
        String fileName = dir + type.getName();
        Enumeration<URL> urls;
        //获取类加载器（应用类加载器）
        ClassLoader classLoader = findClassLoader();
        if (classLoader != null) {
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
        }

        if (urls != null) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                BufferedReader reader = null;

                try {
                    try {
                        reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                        try {
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                final int ci = line.indexOf('#');
                                if (ci >= 0) line = line.substring(0, ci);
                                line = line.trim();
                                if (line.length() > 0) {
                                    try {
                                        String name = null;
                                        int i = line.indexOf('=');
                                        if (i > 0) {
                                            //name 是文件中的key
                                            name = line.substring(0, i).trim();
                                            line = line.substring(i + 1).trim();
                                        }
                                        if (line.length() > 0) {
                                            // 加载类的类型是否是 DistributedLock的类型
                                            Class<?> clazz = Class.forName(line, true, classLoader);
                                            if (!type.isAssignableFrom(clazz)) {
                                                throw new IllegalStateException("Error when load extension class(interface: " +
                                                        type + ", class line: " + clazz.getName() + "), class "
                                                        + clazz.getName() + "is not subtype of interface.");
                                            }
                                            try {
                                                //查找wrapper 类
                                                clazz.getConstructor(type);
                                                Set<Class<?>> wrappers = cachedWrapperClasses;
                                                if (wrappers == null) {
                                                    cachedWrapperClasses = new ConcurrentHashSet<Class<?>>();
                                                    wrappers = cachedWrapperClasses;
                                                }
                                                wrappers.add(clazz);
                                            } catch (NoSuchMethodException e) {//这里抛出异常后解析非warpper类
                                                clazz.getConstructor();
                                                //name 是文件中的key
                                                if (name == null || name.length() == 0) {
                                                    throw new IllegalStateException("No such extension name for the class " + clazz.getName() + " in the config " + url);
                                                }
                                                //name 按都逗号分隔，可以配置多个
                                                //如：redis,redis_1=com.gpmall.commons.lock.impl.DistributedRedisLock
                                                String[] names = NAME_SEPARATOR.split(name);
                                                if (names != null && names.length > 0) {
                                                    for (String n : names) {
                                                        //缓存扩展类对应的的名称：
                                                        // DistributedRedisLock=redis
                                                        // DistributedRedisLock=redis_1
                                                        if (!cachedNames.containsKey(clazz)) {
                                                            cachedNames.put(clazz, n);
                                                        }
                                                        Class<?> c = extensionClasses.get(n);
                                                        if (c == null) {
                                                            //缓存名称对应的扩展类：
                                                            // redis=DistributedRedisLock
                                                            // redis_1=DistributedRedisLock
                                                            extensionClasses.put(n, clazz);
                                                        } else if (c != clazz) {
                                                            throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + n + " on " + c.getName() + " and " + clazz.getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Throwable t) {
                                        IllegalStateException e = new IllegalStateException("Failed to load extension class(interface: " + type + ", class line: " + line + ") in " + url + ", cause: " + t.getMessage(), t);
                                    }
                                }
                            }
                        } catch (Throwable e) {
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }


    private static ClassLoader findClassLoader() {
        return ExtensionLoader.class.getClassLoader();
    }

    private static void check(Class type) {
        if (type == null)
            throw new IllegalArgumentException("Extension type == null");
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }
        if (!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type(" + type +
                    ") is not extension, because WITHOUT @" + LockSpi.class.getSimpleName() + " Annotation!");
        }
    }

    private static <T> boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(LockSpi.class);
    }
}
