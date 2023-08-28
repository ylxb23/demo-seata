package com.zero.demos.seata.msa.acl.port.clients;

import com.zero.demos.seata.msa.acl.pl.AccountItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ylxb2
 */
@FeignClient(name = "microserviceB", contextId = "microserviceB", fallback = AccountServiceFallback.class)
public interface AccountService {

    @GetMapping(path = "/account")
    ResponseEntity<AccountItem> getById(@RequestParam("id") Long id);

    @PostMapping(path = "/account")
    ResponseEntity<String> accAccount(@RequestBody AccountItem account);
}
