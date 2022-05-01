package com.nango.skeletonweb.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Data
@TableName("t_address")
public class AddressDO
        extends Model<AddressDO> {
    private Long id;
    private String code;
    private String name;
    private String pid;
    private Integer type;
    private Integer lit;
    private LocalDateTime createTime;
}
