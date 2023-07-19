package com.pxzq.travel_plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pxzq.travel_plan.entity.Plan;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.mapper.UserMapper;
import com.pxzq.travel_plan.service.PlanService;
import com.pxzq.travel_plan.service.UserService;
import com.pxzq.travel_plan.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    RedisUtil redisUtil;
    @Autowired
    private PlanService planService;

    @Override
    public void delUser(Long userId, String userName) {
        //多表删除
        this.removeById(userId);
        //删除关联信息
        LambdaQueryWrapper<Plan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Plan::getUserId, userId);
        planService.remove(queryWrapper);
        redisUtil.delete(userName);
    }
}
