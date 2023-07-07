package com.pxzq.travel_plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.mapper.UserMapper;
import com.pxzq.travel_plan.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
