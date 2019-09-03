package com.gpmall.commons.lock.aspect;

import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.annotation.CustomerLock;
import com.gpmall.commons.lock.extension.ExtensionLoader;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author: zm
 * @createDate:
 * @description:
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class DistributedLockAspect {


    public static final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);

    //切入点：有这个注解的方法
    @Pointcut("@annotation(com.gpmall.commons.lock.annotation.CustomerLock)")
    public void distributedLockPointcut() {
    }
    //环绕通知，通知+切入点(distributedLockPointcut) = 切面
    @Around("distributedLockPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //组成key
        //切点所在的类
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        //根据注解获取锁对应的key
        final String lockKey = getLockKey(method, pjp.getArgs());
        return startLock(lockKey, pjp, method);
    }

    private Object startLock(final String lockKey, ProceedingJoinPoint pjp, Method method) throws Throwable {
        CustomerLock annotation = method.getAnnotation(CustomerLock.class);
         //是否尝试获得锁 com.gpmall.commons.lock.annotation.CustomerLock.tryLock
        boolean tryLock = annotation.tryLock();
        if (tryLock) {
            return tryLock(pjp, annotation, lockKey);
        } else {
            return lock(pjp, annotation, lockKey);
        }
    }

    private Object lock(ProceedingJoinPoint pjp, CustomerLock annotation, String lockKey) throws Throwable {
        int leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        String type = annotation.lockType();
        //获得分布式锁的具体实现类
        DistributedLock distributedLock = getByType(type);
        try {
            distributedLock.lock(lockKey, timeUnit, leaseTime);//获得不到会抛出异常
            return pjp.proceed();
        } finally {
            distributedLock.unlock(lockKey);
        }
    }


    private Object tryLock(ProceedingJoinPoint pjp, CustomerLock customerLock, String lockKey) throws Throwable {
        int leaseTime = customerLock.leaseTime();
        int waitTime = customerLock.waitTime();
        TimeUnit timeUnit = customerLock.timeUnit();
        String type = customerLock.lockType();
        DistributedLock distributedLock = getByType(type);

        try {
            //如果等待时间为0，则redis如果获得不到锁就直接返回false
            if (waitTime == 0) {
                if (distributedLock.tryLock(lockKey)) {
                    //执行业务方法
                    return pjp.proceed();
                }
            } else {
                //如果等待时间不等于0，则redis最长的等待时间为waitTime
                // 如果没有获得锁则放回false
                //如果获得锁则放回true，让后上锁后自动释放锁时间leaseTime
                distributedLock.tryLock(lockKey, timeUnit, waitTime, leaseTime);
                //执行业务方法
                return pjp.proceed();
            }
        } finally {
            distributedLock.unlock(lockKey);
        }
        return null;
    }


    /**
     * 生成分布式锁key
     *
     * @param method
     * @param args
     * @return
     */
    public String getLockKey(Method method, Object[] args) {
        Objects.requireNonNull(method);
        CustomerLock annotation = method.getAnnotation(CustomerLock.class);
        String lockKey = parseKey(annotation.lockKey(), method, args),
                separator = annotation.separator(),
                prefix = annotation.lockPrefix(),
                suffix = annotation.lockSuffix();
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException(String.format("lock [%s] is error", lockKey));
        }
        StringBuilder keyGenerator = new StringBuilder();
        if (StringUtils.isNotBlank(prefix)) {
            keyGenerator.append(prefix).append(separator);
        }
        keyGenerator.append(lockKey.trim());
        if (StringUtils.isNotBlank(suffix)) {
            keyGenerator.append(separator).append(suffix);
        }
        lockKey = keyGenerator.toString().trim();
        // key不允许为空
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException("Can't get or generate lock accurately!");
        }
        logger.info("generator lock_key [" + lockKey + "]");
        return lockKey;
    }


    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     */
    private String parseKey(String key, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

    /**
     * type =  customerLock.lockType
     * @param type
     * @return
     */
    private DistributedLock getByType(String type) {
        return (DistributedLock) ExtensionLoader.getExtensionLoader(DistributedLock.class).getExtension(type);
    }
}
