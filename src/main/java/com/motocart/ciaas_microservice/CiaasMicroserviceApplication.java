package com.motocart.ciaas_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.motocart")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.motocart")
@EnableScheduling
@EnableCaching
public class CiaasMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiaasMicroserviceApplication.class, args);
    }

}
