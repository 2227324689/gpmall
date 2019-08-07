package com.gpmall.user.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.user.constants.SysRetCodeConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-13:11
 */
@Data
@ApiModel("会员信息更新")
public class UpdateMemberRequest extends AbstractRequest {
    private static final long serialVersionUID = -2337060153713102164L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "会员标识", required = true)
    private Long id;
    /**
     * 注册手机号
     */
    @ApiModelProperty("注册手机号")
    private String phone;
    /**
     * 注册邮箱
     */
    @ApiModelProperty("注册邮箱")
    private String email;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String file;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    private Integer points;
    private Long balance;

    @ApiModelProperty(required = true)
    private String token;

    @Override
    public void requestCheck() {
        if(null == id){
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
