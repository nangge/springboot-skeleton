package com.none.sharding.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Data
@TableName("t_user")
public class UserDO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Integer cityId;
    private Date createTime;
    private Integer sex;
}