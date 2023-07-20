package com.pxzq.travel_plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pxzq.travel_plan.common.R;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.service.UserService;
import com.pxzq.travel_plan.utils.JwtUtil;
import com.pxzq.travel_plan.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
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
        log.warn("login");
        //用户登录
        String userName = user.getUserName();
        String password = user.getPassword();
        log.info("用户信息为:{}", user);
        //1.判断数据库是否有该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userName != null, User::getUserName, userName);
        //2.如果有判断该用户输入的密码是否正确
        queryWrapper.eq(password != null, User::getPassword, DigestUtils.md5DigestAsHex(Objects.requireNonNull(password).getBytes()));
        queryWrapper.eq(User::getStatus, "1");
        //3.查询
        User userServiceOne = userService.getOne(queryWrapper);
        //4判断
        if (userServiceOne != null) {
            String token = JwtUtil.getToken(userName, password,
                    userServiceOne.getId());
            //存储token
            redisUtil.set(userName, token, 86400);
            //返回成功结果
            return R.success(token);
        }
        return R.error("登录失败!");
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/reg")
    public R<String> reg(@RequestBody User user) {
        log.warn("reg");
        String userName = user.getUserName();
        String password = user.getPassword();
        //1判断用户名是否重复
        log.info("用户信息:{}", user);
        user.setUpdateTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setStatus(1);
        user.setName("user");
        this.userService.save(user);
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName()));
        String token = JwtUtil.getToken(userName, user.getPassword(), one.getId());
        redisUtil.set(userName, token, 86400);
        log.info("线程id为:{}", Thread.currentThread().getName());
        return R.success(token);
    }

    /**
     * 列出当前用户信息
     *
     * @param request
     * @return
     */

    /**
     * logout
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        log.warn("logout");
        String token = request.getHeader("token");
        log.info("token:{}", token);
        try {
            User parse = JwtUtil.parse(token);
            redisUtil.del(Objects.requireNonNull(parse).getUserName());
            return R.success("登出成功!");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @PostMapping("/modifyUserInfo")
    public R<String> modify(@RequestBody User user, HttpServletRequest request, String oldPassword) {
        String token = request.getHeader("token");
        User parse = JwtUtil.parse(token);
        assert parse != null;
        Long id = parse.getId();

        log.info("User:{},oldPassword:{}", user, oldPassword);
        //构造器
        if (oldPassword == null || parse.getPassword().equals(oldPassword)) {
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getId, id);
            String password = user.getPassword();
            String email = user.getEmail();
            String phoneNum = user.getPhoneNum();
            updateWrapper.set(password != null, User::getPassword, DigestUtils.md5DigestAsHex(Objects.requireNonNull(password).getBytes(StandardCharsets.UTF_8)));
            updateWrapper.set(email != null, User::getEmail, email);
            updateWrapper.set(phoneNum != null, User::getPhoneNum, phoneNum);
            updateWrapper.set(User::getUpdateTime, new Date());
            userService.update(updateWrapper);
            String token1 = JwtUtil.getToken(parse.getUserName(), password, parse.getId());
            redisUtil.set(parse.getUserName(), token1, 86400);
            return R.success(token1);
        }
        return R.error("修改用户信息失败");

    }

    @DeleteMapping("/del")
    public R<String> del(HttpServletRequest request) {
        String token = request.getHeader("token");
        User parse = JwtUtil.parse(token);
        this.userService.delUser(Objects.requireNonNull(parse).getId(), parse.getUserName());
        return R.success("删除用户成功!");
    }

}
