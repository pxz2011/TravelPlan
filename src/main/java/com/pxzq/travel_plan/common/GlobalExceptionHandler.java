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
        log.error("异常信息为:{}", ex.getMessage());
        //添加用户重复
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] s = ex.getMessage().split(" ");
            String message = "用户:" + s[2] + "已存在!";
            return R.error(message);
        }
        return R.error("未知错误!");
    }
}