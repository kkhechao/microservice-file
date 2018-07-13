package com.zqkh.file.context;

import com.jovezhao.nest.starter.EnableNest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@RefreshScope
@EnableNest
@MapperScan(basePackages = {"com.zqkh.file.context.appservice.impl.domain.repository"})
@ComponentScans({
        @ComponentScan("com.zqkh.file.context"),
        @ComponentScan("com.zqkh.common.configuration")
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}