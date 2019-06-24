package com.springboot.springredisidempotence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.springboot.springredisidempotence.mapper")
@EnableScheduling
public class SpringRedisIdempotenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisIdempotenceApplication.class, args);
    }

}
