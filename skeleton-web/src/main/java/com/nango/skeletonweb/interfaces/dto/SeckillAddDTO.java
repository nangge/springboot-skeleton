package com.nango.skeletonweb.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyn
 * @package com.nango.skeletonweb.interfaces.dto
 * @class SeckillAddDTO
 * @date 2022/4/4 21:04
 * @description 添加秒杀
 */
@Data
public class SeckillAddDTO {

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("秒杀库存")
    private Integer stock;

    @ApiModelProperty("秒杀开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    private LocalDateTime endTime;
}
