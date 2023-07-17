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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            //分页查询
            LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            log.info("线程id为:{}", Thread.currentThread().getName());
            planLambdaQueryWrapper.eq(Plan::getUserId, Objects.requireNonNull(user).getId());
            if (cond != null && !cond.equals("")) {
                planLambdaQueryWrapper.like(Plan::getPlace, cond).or();
                planLambdaQueryWrapper.like(Plan::getThing, cond).or();
                planLambdaQueryWrapper.like(Plan::getTime, cond).or();
                planLambdaQueryWrapper.like(Plan::getRemark, cond);
            }
            planLambdaQueryWrapper.orderByDesc(Plan::getTime);
            planService.page(page, planLambdaQueryWrapper);
            return R.success(page);

        } catch (Exception e) {
            return R.error("token验证失败!");
        }
    }

    @PutMapping("/save")
    public R<String> save(@RequestBody Plan plan, HttpServletRequest request) {
        log.info("数据为:{}", plan);
        String token = request.getHeader("token");
        try {
            User user = JwtUtil.parse(token);
            plan.setUserId(Objects.requireNonNull(user).getId());
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        plan.setUpdateTime(new Date());
        if (plan.getRemark() == null || plan.getRemark().equals("")) {
            plan.setRemark("无");
        }
        planService.save(plan);
        return R.success("添加成功!");
    }

    @DeleteMapping("/{id}")
    public R<String> del(@PathVariable Long id) {
        log.info("id为:{}", id);
        boolean removeById = this.planService.removeById(id);
        if (removeById) {
            return R.success("删除成功!");
        } else {
            return R.error("删除失败");
        }

    }
}
