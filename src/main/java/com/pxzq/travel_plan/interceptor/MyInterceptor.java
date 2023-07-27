package com.pxzq.travel_plan.interceptor;

import com.pxzq.travel_plan.common.OauthContext;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.utils.JwtUtil;
import com.pxzq.travel_plan.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisUtil redisUtil;

    private void returnJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
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
            //判断token
            //验证失败
            if (null == token || "".equals(token) || !jwtUtil.verify(token)) {
                throw new RuntimeException("token验证失败!");
            }
            //验证成功
            else {
                User parse = JwtUtil.parse(token);
                if (parse != null) {
                    String s = JwtUtil.getToken(parse.getUserName(), parse.getPassword(), parse.getId());
                    redisUtil.setOrUpdate(parse.getUserName(), s, 604800);
//                    String json = JSONObject.toJSONString(R.success(s));
//                    returnJson(response,json);
                    OauthContext.set(s);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
