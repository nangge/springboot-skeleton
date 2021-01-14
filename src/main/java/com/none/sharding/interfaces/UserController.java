package com.none.sharding.interfaces;

import com.none.sharding.domain.entity.UserDO;
import com.none.sharding.domain.service.UserService;
import com.none.sharding.infrastruc.persistence.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description 用户
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserService userService;
    @RequestMapping("save")
    public String save(@RequestParam Integer num) {
        ArrayList<UserDO> userOrderDOS = new ArrayList<>();
        for(int i=0;i<num;i++) {
            UserDO userDO = new UserDO();
            userDO.setEmail("safasfsaf");
            userDO.setUserName("safasfsaf");
            userDO.setNickname("safasfsaf");
            userDO.setPassword("123456");
            userDO.setAge(100);
            userDO.setBalance(BigDecimal.TEN);
            userDO.setPhone("18709898767");
            userOrderDOS.add(userDO);
        }

        userService.saveBatch(userOrderDOS);
        return "success";
    }

    @RequestMapping("createTable")
    public String createTable() {
        jdbcTemplate.update(
                "CREATE TABLE `t_user` (\n" +
                        "  `id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '',\n" +
                        "  `user_name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '',\n" +
                        "  `nickname` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '',\n" +
                        "  `age` int(6) NOT NULL COMMENT '账期',\n" +
                        "  `password` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '',\n" +
                        "  `balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '',\n" +
                        "  `phone` varchar(20) NOT NULL DEFAULT '0.00' COMMENT '',\n" +
                        "  `email` varchar(20) NOT NULL DEFAULT '0.00' COMMENT '',\n" +
                        "  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '',\n" +
                        "  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除  0：未删除  1：以删除',\n" +
                        "  `biz_key` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '',\n" +
                        "  `data_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '',\n" +
                        "  PRIMARY KEY (`id`) USING BTREE\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户表'");
        return "success";
    }

    @RequestMapping("address/createTable")
    public String createAddressTable() {
        jdbcTemplate.update(
                "CREATE TABLE `t_address` (\n" +
                        "            `id` bigint(20) NOT NULL,\n" +
                        "  `code` varchar(64) DEFAULT NULL COMMENT '编码',\n" +
                        "            `name` varchar(64) DEFAULT NULL COMMENT '名称',\n" +
                        "            `pid` varchar(64) NOT NULL DEFAULT '0' COMMENT '父id',\n" +
                        "            `type` int(11) DEFAULT NULL COMMENT '1国家2省3市4县区',\n" +
                        "            `lit` int(11) DEFAULT NULL,\n" +
                        "            `create_time` DATETIME NULL,\n" +
                        "  `user_id` bigint(11) DEFAULT NULL,\n" +
                        "    PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return "success";
    }

    @RequestMapping("order/createTable")
    public String createOrderTable() {
        jdbcTemplate.update(
                "CREATE TABLE `t_user_order` (\n" +
                        "  `id` bigint(20) NOT NULL,\n" +
                        "  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',\n" +
                        "  `user_id` int(12) DEFAULT NULL COMMENT '用户id',\n" +
                        "  `amount` int(1) DEFAULT NULL COMMENT '金额',\n" +
                        "`create_time` DATETIME NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return "success";
    }

    @RequestMapping("product/createTable")
    public String createProductTable() {
        jdbcTemplate.update(
                "CREATE TABLE `t_product` (\n" +
                        "  `id` bigint(20) NOT NULL,\n" +
                        "  `name` varchar(64) DEFAULT NULL COMMENT '商品名',\n" +
                        "  `code` int(12) DEFAULT NULL COMMENT '商品码',\n" +
                        "  `price` int(1) DEFAULT NULL COMMENT '金额',\n" +
                        "`create_time` DATETIME NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        return "success";
    }
}
