package com.pxzq.travel_plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private RedisUtil redisUtil;
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
            String token = JwtUtil.getToken(userName, password);
            //存储token
            redisUtil.set(userName, token, 604800L);
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
        user.setUpdateTime(LocalDateTime.now());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        this.userService.save(user);
        String token = JwtUtil.getToken(userName, user.getPassword());
        redisUtil.set(userName, token, 604800L);
        return R.success(token);
    }

    /**
     * 列出当前用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public R<List<User>> list(HttpServletRequest request) {
        log.warn("list");
        String token = request.getHeader("token");
        log.info("token:{}", token);
        try {
            User user = JwtUtil.parse(token);
            if (Objects.requireNonNull(user).getUserName().equals("admin")) {
                List<User> list = this.userService.list();
                return R.success(list);
            } else {
                return R.success(this.userService.list(new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName())));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error(e.getMessage());
        }
    }

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

}
