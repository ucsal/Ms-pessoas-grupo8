package com.example.mspessoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPessoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPessoasApplication.class, args);
    }
}
