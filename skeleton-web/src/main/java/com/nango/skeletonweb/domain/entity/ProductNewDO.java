package com.nango.skeletonweb.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @Author nango
 * @Date 2020/12/19
 * @Description 商品
 */
@Data
@TableName("t_product_new")
public class ProductNewDO
        extends Model<ProductNewDO> {
    @TableId(value = "id")
    private Long id;
    private String name;
    private Long code;
    private Integer price;
    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    /**
     * '是否删除  0：未删除  1：以删除'
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag = 0;

}
