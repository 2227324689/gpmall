package com.gpmall.user.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-13:10
 */
@Data
public class UserLoginResponse extends AbstractResponse {

    private Long id;
    private String username;
    private String phone;
    private String email;
    private String sex;
    private String address;
    private String file;
    private String description;
    private Integer points;
    private Long balance;
    private int state;
    private String token;
}
