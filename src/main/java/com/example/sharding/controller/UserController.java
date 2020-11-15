package com.example.sharding.controller;

import com.example.sharding.entity.User;
import com.example.sharding.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/user/save")
    public String save() {
        for (int i = 0; i <10 ; i++) {
            User user=new User();
            user.setName("test"+i);
            user.setCityId(1%2==0?1:2);
            user.setCreateTime(new Date());
            user.setSex(i%2==0?1:2);
            user.setPhone("11111111"+i);
            user.setEmail("xxxxx");
            user.setCreateTime(new Date());
            user.setPassword("eeeeeeeeeeee");
            userMapper.save(user);
        }

        return "success";
    }

    @RequestMapping("/user/get/{id}")
    public User get(@PathVariable Long id) {
        User user =  userMapper.get(id);
        System.out.println(user.getId());
        return user;
    }

    @RequestMapping("/user/getBySex/{sex}")
    public User get(@PathVariable Integer sex) {
        User user =  userMapper.getBySex(sex);
        System.out.println(user.getId());
        return user;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/user/createTable")
    public String createTable() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS t_account (flowtime VARCHAR(50) NOT NULL, value INT NOT NULL)");
        return "success";
    }
}
