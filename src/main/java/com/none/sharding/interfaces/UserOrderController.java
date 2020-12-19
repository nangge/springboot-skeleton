package com.none.sharding.interfaces;

import com.none.sharding.domain.entity.UserOrderDO;
import com.none.sharding.domain.service.UserOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author wyn
 * @Date 2020/12/19
 * @Description 用户订单
 */
@RestController
@RequestMapping("/userOrder/")
public class UserOrderController {
    @Resource
    private UserOrderService userOrderService;

    /**
     * 生成订单
     * @return
     */
    @RequestMapping("save")
    public String save() {
        UserOrderDO userOrderDO = new UserOrderDO();
        userOrderDO.setOrderNo(String.valueOf(System.currentTimeMillis()));
        userOrderDO.setUserId((long)123);
        userOrderDO.setAmount(500);
        userOrderService.save(userOrderDO);
        return "success";
    }

    /**
     * 通过订单id查询订单
     * @param id
     * @return
     */
    @RequestMapping("get")
    public UserOrderDO getOrderById(@RequestParam Long id) {
        return userOrderService.getById(id);
    }
}
