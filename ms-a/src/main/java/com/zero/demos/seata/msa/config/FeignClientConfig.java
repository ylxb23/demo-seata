package com.zero.demos.seata.msa.config;

import com.zero.demos.seata.msa.acl.port.clients.AccountService;
import com.zero.demos.seata.msa.acl.port.clients.AccountServiceFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public AccountService echoClientFallback() {
        return new AccountServiceFallback();
    }
}
