package com.pxzq.travel_plan.common;

import com.pxzq.travel_plan.service.exception.TokenException;
import com.pxzq.travel_plan.service.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
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
        print(ex, "SQL异常");
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
        print(ex, "空指针异常!");
        return R.error(ex.getMessage());
    }

    /**
     * token错误异常处理
     *
     * @param ex 异常
     * @return 异常信息
     */
    @ExceptionHandler(TokenException.class)
    public R<String> ex04(TokenException ex) {
        print(ex, "token异常!");
        return R.error(ex.getMessage());
    }

    /**
     * 未知异常
     *
     * @param ex 异常
     * @return 异常信息
     */
    @ExceptionHandler(UnknownException.class)
    public R<String> ex05(UnknownException ex) {
        print(ex, "未知异常");
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(MailException.class)
    public R<String> ex06(MailException ex) {
        print(ex, "邮件发送异常!");
        return R.error(ex.getMessage());
    }

    private void print(Exception ex, String exName) {
        ex.printStackTrace();
        String exMessage = ex.getMessage();
        log.error(exName + "异常信息为:{}", exMessage);
    }
}
