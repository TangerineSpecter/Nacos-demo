package com.tangerspecter;

import com.tangerinespecter.IStockService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDubbo //服务发现
public class OrderApplication {

    @Bean
    @LoadBalanced //负载均衡
    public RestTemplate create() {
        return new RestTemplate();
    }

    @DubboReference(version = "1.0.0", group = "orange")
    public IStockService stockService;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
