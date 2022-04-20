package com.nango.skeletonweb.domain.order.enums;

/**
 * @author wyn
 * @package com.nango.skeletonweb.domain.order.stateMachine
 * @class StatesEnum
 * @date 2022/1/11 22:36
 * @description 订单状态枚举
 */
public enum StatesEnum {
    WAIT_PAY,
    CANCEL,
    PAIED,
    REFUND,
    DELETED
}
