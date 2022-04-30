package com.nango.skeletonweb.infrastructure.persistence.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nango.skeletonweb.infrastructure.persistence.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author nango
 * @since 2022-04-03
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    @TableName("t_order")
public class Order extends BaseModel {

    private static final long serialVersionUID = 1L;

      /**
     * 用户id
     */
      private Integer userId;

      /**
     * 商品id
     */
      private Integer productId;

      /**
     * 订单总价
     */
      private Integer amount;

      /**
     * 支付状态
     */
      private Integer payStatus;

    private Integer delFlag;


}
