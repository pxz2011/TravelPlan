package com.pxzq.travel_plan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
//启动程序
public class TravelPlanApplication {
    /**
     * Main
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        log.info(Arrays.toString(args));
        SpringApplication.run(TravelPlanApplication.class, args);
        log.info("项目启动成功!...");
    }

}
