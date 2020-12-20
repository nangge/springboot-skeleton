package com.none.sharding.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description 用户订单
 */
@Data
@TableName("t_user_order")
public class UserOrderDO
        extends Model<UserOrderDO> {
    private Long id;
    private String orderNo;
    private Long userId;
    private Integer amount;
    private LocalDateTime createTime;
}
