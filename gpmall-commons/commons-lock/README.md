##关于commons-lock 使用

#注意事项 
  * 启动类上需要扫描该包下的组件,添加@ComponentScan(basePackages = {"com.gpmall.commons.lock"})
  
##使用规则
  * 在需要使用分布式锁的方法上添加注解  @CustomerLock(lockKey = "#id") 
     
    # 注解中各个参数详解:
     *  lockKey   key可以自定义也可以使用方法中的参数(遵循spring el表达式)
     *  lockPrefix   key前缀
     *  lockSuffix   后缀
     *  separator    分割符
     *  最后组成的key为lockPrefix+separator+lockKey+separator+lockSuffix
     *  tryLock 是否使用尝试锁  默认不适用
     *  waitTime  尝试锁的等待时间
     *  leaseTime  锁的超时时间默认30
     *  timeUnit   时间单位 默认为秒
 


#关于扩展点(伪扩展点) lockType
  * lockType 借鉴dubbo的spi规则 默认使用redis 扩展点
  * 若需要扩展  1 实现DistributedLock接口 2在指定资源目录(META-INF/services/ 或者 META-INF/lock/
    或者META-INF/lock/internal/)下添加key=value (可以参考 gpmall-commons\commons-lock\src\main\resources\META-INF\lock\com.gpmall.commons.lock.DistributedLock)
  * 关于wrapper 可以自定义策略对key进行处理 
     