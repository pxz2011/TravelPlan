package com.pxzq.travel_plan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.mapper.UserMapper;
import com.pxzq.travel_plan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class test01 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        user.setEmail("1521955177@qq.com");
        user.setUserName("pxzq");
        user.setName("彭啸洲");
        user.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
        user.setStatus(1);
        user.setPhoneNum("13161288127");
        user.setUpdateTime(LocalDateTime.now());
        user.setId(1L);
        userMapper.insert(user);
    }

    @Test
    public void check() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, "pxzq");
        lambdaQueryWrapper.eq(User::getPassword, DigestUtils.md5DigestAsHex("laofengwukun8905^".getBytes()));
        User one = userService.getOne(lambdaQueryWrapper);
        log.info(one.toString());

    }


}
