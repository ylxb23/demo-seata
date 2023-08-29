package com.zero.demos.seata.msb.ohs.controller;

import com.zero.demos.seata.msb.domain.AccountService;
import com.zero.demos.seata.msb.ohs.pl.AccountChangeBalanceRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemResponse;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylxb2
 */
@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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

    /**
     * 扣除余额
     * @param request
     * @return
     */
    @PostMapping(path = "/account/deduction")
    public ResponseEntity<String> deductionBalance(@RequestBody AccountChangeBalanceRequest request) {
        logger.info("controller: account deduction get global transaction id: {}", RootContext.getXID());
        // 请求的金额一定是正数，强制将金额变更为负值
        if(request == null || request.getId() < 1 || request.getAmount() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        request.setAmount(-request.getAmount());
        int balance = accountService.changeBalance(request);
        if(balance > -1) {
            return ResponseEntity.ok("After deduction, balance is: " + balance);
        }
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("balance not enough");
    }

    /**
     * 增加金额
     * @param request
     * @return
     */
    @PostMapping(path = "/account/increase")
    public ResponseEntity<String> increaseBalance(@RequestBody AccountChangeBalanceRequest request) {
        logger.info("controller: account deduction get global transaction id: {}", RootContext.getXID());
        // 请求的金额一定是正数
        if(request == null || request.getId() < 1 || request.getAmount() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int balance = accountService.changeBalance(request);
        if(balance > -1) {
            return ResponseEntity.ok("After deduction, balance is: " + balance);
        }
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body("balance not enough");
    }

}
