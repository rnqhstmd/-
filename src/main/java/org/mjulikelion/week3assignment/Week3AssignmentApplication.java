package org.mjulikelion.week3assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week3AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week3AssignmentApplication.class, args);
    }

}
