package com.pxzq.travel_plan.common;

import com.pxzq.travel_plan.service.exception.TokenException;
import com.pxzq.travel_plan.service.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@ResponseBody
@Slf4j
/*
    异常处理
 */
public class GlobalExceptionHandler {
    /**
     * sql 异常
     *
     * @param ex 异常
     * @return 错误信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> ex01(SQLIntegrityConstraintViolationException ex) {
        ex.printStackTrace();
        //获取异常信息
        log.warn("SQL异常,异常信息为:{}", ex.getMessage());
        //添加用户重复
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("username")) {
            String message = "该用户已存在";
            return R.error(message);
        }
        if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("email")) {
            String message = "该邮箱已使用";
            return R.error(message);
        }
        return R.error("未知错误");
    }

    /**
     * 空指针
     *
     * @param ex 异常
     * @return 异常信息
     */
    @ExceptionHandler(NullPointerException.class)
    public R<String> ex03(NullPointerException ex) {
        ex.printStackTrace();
        log.error("空指针异常,异常信息为:{},", ex.getMessage());
        return R.error("空指针异常");
    }

    /**
     * token错误异常处理
     *
     * @param ex 异常
     * @return 异常信息
     */
    @ExceptionHandler(TokenException.class)
    public R<String> ex04(TokenException ex) {
        ex.printStackTrace();
        String exMessage = ex.getMessage();
        log.error("token异常,异常信息为:{}", exMessage);
        return R.error(exMessage);
    }

    /**
     * 未知异常
     *
     * @param ex 异常
     * @return 异常信息
     */
    @ExceptionHandler(UnknownException.class)
    public R<String> ex05(UnknownException ex) {
        ex.printStackTrace();
        String exMessage = ex.getMessage();
        log.error("未知异常,异常信息为:{}", exMessage);
        return R.error(exMessage);
    }

}
