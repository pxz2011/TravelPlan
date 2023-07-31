package com.pxzq.travel_plan.service.exception;

import java.io.Serializable;

/**
 * @author pxz
 * @version 1.0
 * @project travel_plan
 * @description token错误类
 * @date 2023/7/30 15:11:33
 */

public class TokenException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Token错误
     *
     * @param msg 信息
     */
    public TokenException(String msg) {
        super(msg);
    }
}
