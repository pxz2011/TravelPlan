package com.pxzq.travel_plan.service.exception;

/**
 * @author pxz
 * @version 1.0
 * @project travel_plan
 * @description
 * @date 2023/7/30 15:26:49
 */
public class UnknownException extends RuntimeException {
    public UnknownException(String msg) {
        super(msg);
    }
}
