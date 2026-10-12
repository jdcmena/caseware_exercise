package com.engagement.engagementexercise;

import org.springframework.boot.SpringApplication;

public class TestEngagementExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.from(EngagementExerciseApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
