package com.none.sharding.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author nango
 * @Date 2020/12/19
 * @Description 商品
 */
@Data
@TableName("t_product")
public class ProductDO
        extends Model<ProductDO> {
    private Long id;
    private String name;
    private Long code;
    private Integer price;
    private LocalDateTime createTime;
}
