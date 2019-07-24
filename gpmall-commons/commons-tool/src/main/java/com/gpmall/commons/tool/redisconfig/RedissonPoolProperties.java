package com.gpmall.commons.tool.redisconfig;

import lombok.Data;

/**
 * Created by mic on 2019/7/23.
 */

@Data
public class RedissonPoolProperties {

    private int maxIdle; /**连接池中的最大空闲连接**/

    private int minIdle;  /**最小连接数**/

    private int maxActive;/**连接池最大连接数**/

    private int maxWait;/**连接池最大阻塞等待时间**/

}
