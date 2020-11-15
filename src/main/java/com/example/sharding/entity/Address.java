package com.example.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Data
@TableName("t_address")
public class Address extends Model<Address> {
    private Long id;
    private String code;
    private String name;
    private String pid;
    private Integer type;
    private Integer lit;
}
