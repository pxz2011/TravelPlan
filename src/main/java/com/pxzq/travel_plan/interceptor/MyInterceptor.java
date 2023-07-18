package com.pxzq.travel_plan.interceptor;

import com.pxzq.travel_plan.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("P3P", "CP=CAO PSA OUR");
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept,X-Requested-With");
            response.addHeader("Access-Control-Max-Age", "120");
        }
        //校验登录
        log.info("requestURI:{}", request.getRequestURI());
        //设置不需拦截路径
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        try {
            if (null == token || "".equals(token) || !JwtUtil.verify(token)) {
                throw new RuntimeException("token验证失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return true;
    }

}
