package com.pxzq.travel_plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pxzq.travel_plan.entity.User;

public interface UserService extends IService<User> {
    void delUser(Long userId, String userName);
}
