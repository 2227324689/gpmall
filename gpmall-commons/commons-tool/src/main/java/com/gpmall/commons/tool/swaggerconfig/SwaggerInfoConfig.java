package com.gpmall.commons.tool.swaggerconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * @author tu
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerInfoConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /** 版权年份 */
    private String copyrightYear;

    /** swagger开关 */
    private boolean enabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

}
