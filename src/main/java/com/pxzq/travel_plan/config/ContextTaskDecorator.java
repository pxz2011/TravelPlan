package com.pxzq.travel_plan.config;
/*
  @author pxz
 * @version 1.0
 * @project travel_plan
 * @description 上下文装饰器
 * @date 2023/7/25 14:18:28
 */
/*
  异步线程设置
 */

import com.pxzq.travel_plan.common.OauthContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
@Slf4j
public class ContextTaskDecorator implements TaskDecorator {
    @Override
    @NonNull
    public Runnable decorate(@NonNull Runnable runnable) {
        //获取父线程的loginVal
        String token = OauthContext.get();
        return () -> {
            try {
                log.info("runnable:{},token:{}", runnable, token);
                // 将主线程的请求信息，设置到子线程中
                OauthContext.set(token);
                // 执行子线程，这一步不要忘了
                runnable.run();
            } finally {
                // 线程结束，清空这些信息，否则可能造成内存泄漏
                OauthContext.clear();
            }
        };
    }
}