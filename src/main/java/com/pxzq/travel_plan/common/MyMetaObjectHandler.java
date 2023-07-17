package com.pxzq.travel_plan.common;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
/**
 * 自动填充的字段处理
 * 字段上方需要添加@TableField注解
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建
     * <p>
     * /**
     * 修改时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", date);
        metaObject.setValue("updateTime", date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());
        Date date = new Date();
        metaObject.setValue("updateTime", date);
    }

}