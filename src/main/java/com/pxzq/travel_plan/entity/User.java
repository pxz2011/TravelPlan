package com.pxzq.travel_plan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    //id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    //姓名
    @TableField(fill = FieldFill.INSERT)
    private String name;
    //用户名
    private String userName;
    //密码
    private String password;
    //邮箱(发送验证码)
    private String email;
    //手机号(发送验证码)
    private String phoneNum;
    @TableField(fill = FieldFill.INSERT)
    //创建时间
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    //修改时间
    private Date updateTime;
    //创建用户
    //用户状态
    private Integer status;
}
