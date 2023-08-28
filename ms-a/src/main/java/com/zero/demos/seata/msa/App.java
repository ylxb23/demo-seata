package com.zero.demos.seata.msa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

/**
 * Hello world!
 * @author ylxb2
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.zero.demos.seata.msa.acl.port.clients"})
@EnableAutoConfiguration
@SpringBootApplication
@PropertySource(value = {"classpath:application.yml"})
@ComponentScan(value = {"com.zero.demos.seata.msa"})
@LoadBalancerClients(value = {@LoadBalancerClient("microserviceB"), @LoadBalancerClient("microserviceC")})
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("MS-A App init with args: {}", Arrays.asList(args));
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        logger.info("MS-A App started with {} beans inited, beans: {}", context.getBeanDefinitionCount(), context.getBeanDefinitionNames());
    }
}
