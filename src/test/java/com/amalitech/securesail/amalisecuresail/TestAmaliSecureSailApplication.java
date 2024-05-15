package com.amalitech.securesail.amalisecuresail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAmaliSecureSailApplication {

    public static void main(String[] args) {
        SpringApplication.from(AmaliSecureSailApplication::main).with(TestAmaliSecureSailApplication.class).run(args);
    }

}
