package com.gpmall.commons.lock.impl.zk;

/**
 * Description: 分布式锁---后台线程配置中心
 * 分布式锁是基于zk的锁，加锁时会在zk创建目录，在释放锁后，此目录并不会删掉。
 * 因此需要定时任务，定时清理空目录。
 * @date 2019年8月12日 16:00
 * @author msl 1015952139
 */
public class LockBackGroundConf {

    /** 执行频率, 默认一小时一次, 单位秒 */
    private Long frequency = 60*60L;
    /** 删除几天前的数据, 默认1天前的数据, 单位秒 */
    private Long beforeTime = 24*60*60L;


    public Long getFrequency() {
        return frequency;
    }
    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }
    public Long getBeforeTime() {
        return beforeTime;
    }
    public void setBeforeTime(Long beforeTime) {
        this.beforeTime = beforeTime;
    }
}
