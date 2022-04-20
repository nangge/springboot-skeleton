package com.nango.skeletonweb.domain.order.stateMachine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.nango.skeletonweb.domain.order.enums.OrderEventEnum;
import com.nango.skeletonweb.domain.order.enums.StatesEnum;

/**
 * @author wyn
 * @package com.nango.skeletonweb.domain.order.stateMachine
 * @class OrderStateMachine
 * @date 2022/1/11 22:39
 * @description 订单状态机
 */
public class OrderStateMachine {

    private final String MACHINE_ID = "OrderStateMachine";

    static class Context {
        String operator = "frank";
        String entityId = "123465";
    }

    public static void main(String[] args) {
        StateMachineBuilder<StatesEnum, OrderEventEnum, Context> builder = StateMachineBuilderFactory.create();

        //待支付-》支付
        builder.externalTransition()
                .from(StatesEnum.WAIT_PAY)
                .to(StatesEnum.PAIED)
                .on(OrderEventEnum.PAY)
                .when(checkCondition())
                .perform(doAction());



        builder.externalTransition()
                .from(StatesEnum.WAIT_PAY)
                .to(StatesEnum.CANCEL)
                .on(OrderEventEnum.CANCEL)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(StatesEnum.PAIED)
                .to(StatesEnum.REFUND)
                .on(OrderEventEnum.REFUND)
                .when(checkCondition())
                .perform(doAction());



        builder.build("OrderStateMachine");

        StateMachine<StatesEnum, OrderEventEnum, Context> orderStateMachine = StateMachineFactory.get("OrderStateMachine");
        orderStateMachine.showStateMachine();
        //执行退款事件
        orderStateMachine.fireEvent(StatesEnum.PAIED, OrderEventEnum.REFUND, new Context());
    }

    public  void init() {
        StateMachineBuilder<StatesEnum, OrderEventEnum, Context> builder = StateMachineBuilderFactory.create();

        //待支付-》支付
        builder.externalTransition()
                .from(StatesEnum.WAIT_PAY)
                .to(StatesEnum.PAIED)
                .on(OrderEventEnum.PAY)
                .when(checkCondition())
                .perform(doAction());



        builder.externalTransition()
                .from(StatesEnum.WAIT_PAY)
                .to(StatesEnum.CANCEL)
                .on(OrderEventEnum.CANCEL)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(StatesEnum.PAIED)
                .to(StatesEnum.REFUND)
                .on(OrderEventEnum.REFUND)
                .when(checkCondition())
                .perform(doAction());



        builder.build(MACHINE_ID);

        StateMachine<StatesEnum, OrderEventEnum, Context> orderStateMachine = StateMachineFactory.get(MACHINE_ID);
        orderStateMachine.showStateMachine();
    }

    private static Condition<Context> checkCondition() {
        return (ctx) -> {return true;};
    }

    private static Action<StatesEnum, OrderEventEnum, Context> doAction() {
        return (from, to, event, ctx)->{
            System.out.println(ctx.operator+" is operating "+ctx.entityId+" from:"+from+" to:"+to+" on:"+event);
        };
    }
}
