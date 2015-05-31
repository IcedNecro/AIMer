package com.checkpoint.aimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mangofactory.swagger.plugin.EnableSwagger;

@SpringBootApplication
@EnableSwagger
@EnableAutoConfiguration
public class AimerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimerApplication.class, args);
    }
}
