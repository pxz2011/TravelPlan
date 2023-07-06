package com.pxzq.travel_plan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    //id
    @TableId
    private Long id;
    //用户名
    private String userName;
    //密码
    private String password;
    //邮箱(发送验证码)
    private String email;
    //手机号(发送验证码)
    private String phoneNum;
    //创建时间
    private LocalDateTime createTime;
    //修改时间
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    //创建用户
    private Long createUser;
    @TableField(fill = FieldFill.INSERT)
    //修改用户
    private Long updateUser;
    //用户状态
    private Integer Status;
}
