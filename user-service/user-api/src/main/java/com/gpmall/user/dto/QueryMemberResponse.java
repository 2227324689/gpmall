package com.gpmall.user.dto;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-下午11:50
 */
@Data
public class QueryMemberResponse extends AbstractResponse{

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sex;

    private String address;

    private Integer state;

    private String file;

    private String description;

    private Integer points;

    private BigDecimal balance;
}
