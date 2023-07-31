package com.pxzq.travel_plan.utils;

import java.util.Random;

/**
 * @author pxz
 * @version 1.0
 * @project travel_plan
 * @description 验证码生成
 * @date 2023/7/31 17:02:20
 */
public class CodeUtil {
    static Random randObj = new Random();

    public static String get() {
        return Integer.toString(1000 + randObj.nextInt(9000));
    }
}
