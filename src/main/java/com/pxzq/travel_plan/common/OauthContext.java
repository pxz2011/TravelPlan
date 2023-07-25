package com.pxzq.travel_plan.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author pxz
 * @version 1.0
 * @project travel_plan
 * @description
 * @date 2023/7/25 14:21:03
 */
@Slf4j
public class OauthContext {
    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    public static String get() {
        log.info("token:{}", tokenThreadLocal.get());
        return tokenThreadLocal.get();
    }

    public static void set(String token) {
        tokenThreadLocal.set(token);
    }

    public static void clear() {
        tokenThreadLocal.remove();
    }
}
