package com.pxzq.travel_plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxzq.travel_plan.common.R;
import com.pxzq.travel_plan.entity.Plan;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.service.PlanService;
import com.pxzq.travel_plan.service.UserService;
import com.pxzq.travel_plan.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private UserService userService;
    @Autowired
    private PlanService planService;

    @GetMapping("/page")
    public R<Page<Plan>> page(int pageNum, int pageSize, HttpServletRequest request, String cond) {
        String token = request.getHeader("token");
        log.info("页码为:{},每页数据为:{},用户token为:{},查询条件为:{}", pageNum, pageSize, token, cond);
        //获取token
        Page<Plan> page = new Page<>(pageNum, pageSize);
        try {
            User user = JwtUtil.parse(token);
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(user != null, User::getUserName, Objects.requireNonNull(user).getUserName());
            User user1 = userService.getOne(queryWrapper);
            //分页查询
            LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            planLambdaQueryWrapper.eq(Plan::getUserId, user1.getId());
            if (cond != null && !cond.equals("")) {
                planLambdaQueryWrapper.like(Plan::getPlace, cond).or();
                planLambdaQueryWrapper.like(Plan::getThing, cond).or();
                planLambdaQueryWrapper.like(Plan::getTime, cond);
            }
            planLambdaQueryWrapper.orderByDesc(Plan::getUpdateTime);
            planLambdaQueryWrapper.orderByDesc(Plan::getCreateTime);
            planLambdaQueryWrapper.orderByDesc(Plan::getTime);
            planService.page(page, planLambdaQueryWrapper);
            return R.success(page);

        } catch (Exception e) {
            return R.error("token验证失败!");
        }
    }
}
