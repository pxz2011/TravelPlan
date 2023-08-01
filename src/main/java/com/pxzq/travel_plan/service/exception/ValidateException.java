package com.pxzq.travel_plan.service.exception;

/**
 * @author pxz
 * @version 1.0
 * @project travel_plan
 * @description 验证错误
 * @date 2023/8/1 09:19:32
 */
public class ValidateException extends RuntimeException {
    public ValidateException(String msg) {
        super(msg);
    }
}
