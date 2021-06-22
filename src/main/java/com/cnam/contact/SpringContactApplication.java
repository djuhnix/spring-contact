package com.cnam.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cnam.contact.repository")
public class SpringContactApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringContactApplication.class, args);
    }

}
