package com.gpmall.user.dal.entitys;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String sex;

    private String address;

    private Integer state;

    private String description;

    private Integer roleId;

    private String file;

    private Date created;

    private Date updated;

    private static final long serialVersionUID = 1L;
}