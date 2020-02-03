package com.tc.complier;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tc.complier.dao")
public class ComplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplierApplication.class, args);
    }

}
