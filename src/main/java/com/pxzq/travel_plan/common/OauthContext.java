package com.pxzq.travel_plan.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author pxz
 * @version 1.0
 * {@code @project} travel_plan
 * @description
 * @date 2023/7/25 14:21:03
 */

/**
 * 线程之间传递信息
 */
@Slf4j
public class OauthContext {

    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    /**
     * get
     *
     * @return token
     */
    public static String get() {
        log.info("LocalThreadToken:{}", tokenThreadLocal.get());
        return tokenThreadLocal.get();
    }

    /**
     * 设置
     *
     * @param token token
     */
    public static void set(String token) {
        log.info("setToken,token={}", token);
        tokenThreadLocal.set(token);
    }

    /**
     * 清理
     */
    public static void clear() {
        tokenThreadLocal.remove();
    }
}
