package com.nango.skeletonweb.interfaces;

import com.nango.skeletonweb.infrastructure.config.CommonResponse;
import com.nango.skeletonweb.infrastructure.persistence.user.dao.UserDao;
import com.nango.skeletonweb.infrastructure.persistence.user.entity.User;
import com.nango.skeletonweb.infrastructure.utils.RedisUtil;
import com.nango.skeletonweb.interfaces.dto.DemoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wyn
 * @package com.nango.skeletonweb.interfaces
 * @class DemoController
 * @date 2021/9/2 14:54
 * @description Demo
 */
@RestController
@RequestMapping(("/demo"))
@Api(tags = "Demo")
@Slf4j
public class DemoController {
    @Resource
    private UserDao userDao;

    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "测试")
    @PostMapping("/test")
    public CommonResponse<User> test(@Valid @RequestBody DemoDTO demoDTO) {
        log.info("<<<<< 日志输出 >>>>>>>> {}", 111111);
        System.out.println("demo >>>>>>>>");
        User user = userDao.getById(530792019992248320L);
        redisUtil.set("user", user);
        return CommonResponse.success(user);
    }


    @ApiOperation(value = "redis测试")
    @PostMapping("/redisSet")
    public CommonResponse<User> testRedis(@Valid @RequestBody DemoDTO demoDTO) {
        log.info("<<<<< 日志输出 >>>>>>>> {}", 2);
        User redisT = redisUtil.get("redisT");
        return CommonResponse.success(redisT);
    }
}
