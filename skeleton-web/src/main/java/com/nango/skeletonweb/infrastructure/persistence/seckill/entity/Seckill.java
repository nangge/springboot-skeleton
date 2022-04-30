package com.nango.skeletonweb.infrastructure.persistence.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.nango.skeletonweb.infrastructure.persistence.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀表
 * </p>
 *
 * @author nango
 * @since 2022-04-03
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    @TableName("t_seckill")
public class Seckill extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer productId;

      /**
     * 名称
     */
      private String productName;

      /**
     * 价格
     */
      private Integer price;

      /**
     * 库存
     */
      private Integer stock;

      /**
     * 秒杀开始时间
     */
      private LocalDateTime startTime;

      /**
     * 秒杀结束
     */
      private LocalDateTime endTime;

      /**
     * 0正常 1删除
     */
      private Integer delFlag;


}
