package com.nango.skeletonweb.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Data
@TableName("t_user")
public class UserDO {
    @TableId(value = "id")
    private String id;
    private String userName;
    private String nickname;
    private int age;
    private String password;
    private BigDecimal balance;
    private String phone;
    private String email;

    @TableField(value = "version")
    private Integer version;


    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag = 0;



    @TableField(value = "biz_key", fill = FieldFill.INSERT)
    private String bizKey ="0";


    @TableField(value = "data_status", fill = FieldFill.INSERT)
    private Integer dataStatus = 0;
}