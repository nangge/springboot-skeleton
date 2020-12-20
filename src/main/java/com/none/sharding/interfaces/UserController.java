package com.none.sharding.interfaces;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @RequestMapping("createTable")
    public String createTable() {
        jdbcTemplate.update(
                "CREATE TABLE `t_user` (\n" +
                "  `id` bigint(20) NOT NULL,\n" +
                "  `name` varchar(64) DEFAULT NULL COMMENT '名称',\n" +
                "  `city_id` int(12) DEFAULT NULL COMMENT '城市',\n" +
                "  `sex` tinyint(1) DEFAULT NULL COMMENT '性别',\n" +
                "  `phone` varchar(32) DEFAULT NULL COMMENT '电话',\n" +
                "  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',\n" +
                "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `password` varchar(32) DEFAULT NULL COMMENT '密码',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
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
