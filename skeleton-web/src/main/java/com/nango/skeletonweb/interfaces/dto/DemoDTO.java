package com.nango.skeletonweb.interfaces.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wyn
 * @package com.nango.skeletonweb.interfaces.dto
 * @class DemoDTO
 * @date 2021/9/2 16:38
 * @description demo
 */
@Data
public class DemoDTO {
    @NotNull(message = "名字不能为空")
    private String name;
}
