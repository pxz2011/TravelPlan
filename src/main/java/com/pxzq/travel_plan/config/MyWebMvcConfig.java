package com.pxzq.travel_plan.config;

import com.pxzq.travel_plan.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    /**
     * 跨域请求处理器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .maxAge(3600);

    }

    /**
     * 设置拦截器
     *
     * @param registry 注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/user/login");
        list.add("/user/reg");
        list.add("/user/logout");
        list.add("/user/sendEmail");
        list.add("/user/verifyCode");
        list.add("/error");
        registry.addInterceptor(myInterceptor())
                .excludePathPatterns(list)
                .addPathPatterns("/**");
    }

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(5);
        poolTaskExecutor.setMaxPoolSize(20);
        // 设置线程活跃时间（秒）
        poolTaskExecutor.setKeepAliveSeconds(60);
        // 设置队列容量
        poolTaskExecutor.setQueueCapacity(60);
        //设置TaskDecorator，用于解决父子线程间的数据复用
        poolTaskExecutor.setTaskDecorator(new ContextTaskDecorator());
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return poolTaskExecutor;
    }
}
