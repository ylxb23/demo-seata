package com.zero.demos.seata.msb.ohs.controller;

import com.zero.demos.seata.msb.domain.AccountService;
import com.zero.demos.seata.msb.ohs.pl.AccountItemRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping(path = "/account")
    public ResponseEntity<AccountItemResponse> getById(@RequestParam("id") Long id) {
        AccountItemResponse res = accountService.getById(id);
        if(res == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/account")
    public ResponseEntity<String> addAccount(@RequestBody AccountItemRequest request) {
        if(request != null && !StringUtils.hasText(request.getUserId()) && request.getBalance() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request body invalid");
        }
        String res = accountService.addAccount(request);
        return ResponseEntity.ok(res);
    }

}
