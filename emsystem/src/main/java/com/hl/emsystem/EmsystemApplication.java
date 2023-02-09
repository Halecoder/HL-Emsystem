package com.hl.emsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hl.emsystem.model.dao")
public class EmsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsystemApplication.class, args);
    }

}
