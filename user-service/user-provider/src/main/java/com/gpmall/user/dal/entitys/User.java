package com.gpmall.user.dal.entitys;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_user")
@Data
@ToString
public class User {
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码 md5加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    private String sex;

    private String address;

    private Integer state;

    private String description;

    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 头像
     */
    private String file;

    private Date created;

    private Date updated;


}