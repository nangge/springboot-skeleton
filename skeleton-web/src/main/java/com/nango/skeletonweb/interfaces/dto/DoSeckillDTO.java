package com.nango.skeletonweb.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyn
 * @package com.nango.skeletonweb.interfaces.dto
 * @class DoSeckillDTO
 * @date 2022/4/4 21:26
 * @description 秒杀开始
 */
@Data
public class DoSeckillDTO {
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("秒杀id")
    private Integer seckillId;

    @ApiModelProperty("秒杀数量")
    private Integer num;
}
