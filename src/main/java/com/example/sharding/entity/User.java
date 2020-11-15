package com.example.sharding.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Data
public class User {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Integer cityId;
    private Date createTime;
    private Integer sex;
}