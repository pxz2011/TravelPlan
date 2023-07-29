package com.pxzq.travel_plan.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map<Object, Object> map = new HashMap<>(); //动态数据
    private String token;

    /**
     * 成功
     *
     * @param object 信息
     * @param token  jwt
     * @return 返回一个R对象
     */
    public static <T> R<T> success(T object, String token) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        r.token = token;
        return r;
    }

    /**
     * 错误
     *
     * @param msg 信息
     * @return 返回R对象
     */
    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
