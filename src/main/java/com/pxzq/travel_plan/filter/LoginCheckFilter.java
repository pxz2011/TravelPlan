package com.pxzq.travel_plan.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转换类型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //涉及跨域请求 所以需要获取当前url
        StringBuffer requestURL = request.getRequestURL();
        log.info("访问URL为:{}", requestURL);
        //设置不需要拦截的路径
        String[] urls = {

        };
        boolean checkPath = checkPath(urls, requestURL);
        if (checkPath) {
            filterChain.doFilter(request, response);
        }
//        if (jwtUtil.parseJWT(request.getHeader("token")) != null) {
//
//        }
    }

    /**
     * 路径匹配
     *
     * @param urls
     * @param requestUrl
     * @return
     */
    private boolean checkPath(String[] urls, StringBuffer requestUrl) {
        for (String url : urls) {
            return antPathMatcher.match(url, String.valueOf(requestUrl));
        }
        return false;
    }
}
