package com.zero.demos.seata.seata.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"io.seata"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(io.seata.server.ServerApplication.class, args);
    }
}
