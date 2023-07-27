package com.pxzq.travel_plan.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> ex01(SQLIntegrityConstraintViolationException ex) {
        //获取异常信息
        log.warn("异常信息为:{}", ex.getMessage());
        //添加用户重复
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("username")) {
            String message = "该用户已存在!";
            return R.error(message);
        }
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("email")) {
            String message = "该邮箱已使用!";
            return R.error(message);
        }
        return R.error("未知错误!");
    }

    @ExceptionHandler(RuntimeException.class)
    public R<String> ex02(RuntimeException ex) {
        log.error("运行异常信息为:{}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("username")) {
            String message = "该用户已存在!";
            return R.error(message);
        }
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("email")) {
            String message = "该邮箱已使用!";
            return R.error(message);
        }
        if (ex.getMessage().contains("token") && ex.getMessage().contains("logout")) {
            log.warn("退出登录,token失效");
            //直接跳过,如果不人为修改redis,则redis过期时间与token过期时间是一样的,所以redis的key已经自动删除,不再处理
            return R.success("登出成功!", null);
        }
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public R<String> ex03(NullPointerException ex) {
        log.error("空指针异常,异常信息为:{},", ex.getMessage());
        return R.error("空指针异常!,异常信息为:" + ex.getMessage());
    }

}
