package com.pxzq.travel_plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pxzq.travel_plan.common.R;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.service.UserService;
import com.pxzq.travel_plan.utils.JwtUtil;
import com.pxzq.travel_plan.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    RedisUtil redisUtil;
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * param1:userName
     * param2:password
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody User user) {
        //用户登录
        String userName = user.getUserName();
        String password = user.getPassword();
        log.info("用户信息为:{}", user.toString());
        //1.判断数据库是否有该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userName != null, User::getUserName, userName);
        //2.如果有判断该用户输入的密码是否正确
        queryWrapper.eq(password != null, User::getPassword, DigestUtils.md5DigestAsHex(Objects.requireNonNull(password).getBytes()));
        //3.查询
        User userServiceOne = userService.getOne(queryWrapper);
        //4判断
        if (userServiceOne != null) {
            String token = JwtUtil.getToken(userName, password);
            //存储token
            redisUtil.set(userName, token);
            //返回成功结果
            return R.success(token);
        }
        return R.error("登录失败!");

    }
}
