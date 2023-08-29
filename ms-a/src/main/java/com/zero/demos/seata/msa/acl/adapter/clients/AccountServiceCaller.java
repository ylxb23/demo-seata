package com.zero.demos.seata.msa.acl.adapter.clients;

import com.zero.demos.seata.msa.acl.pl.AccountBalanceChangeRequest;
import com.zero.demos.seata.msa.acl.pl.AccountItem;
import com.zero.demos.seata.msa.acl.port.clients.AccountService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 防止Feign对非成功状态码抛出异常
 * @author ylxb2
 */
@Service
public class AccountServiceCaller {
    @Autowired
    private AccountService accountService;

    public ResponseEntity<AccountItem> getById(Long id) {
        try {
            return accountService.getById(id);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            throw e;
        }
    }


    public ResponseEntity<String> accAccount(AccountItem account) {
        try {
            return accountService.accAccount(account);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.responseBody().get().toString());
        } catch (Exception e) {
            throw e;
        }
    }



    public ResponseEntity<String> deductionBalance(AccountBalanceChangeRequest request) {
        try {
            return accountService.deductionBalance(request);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.responseBody().get().toString());
        } catch (Exception e) {
            throw e;
        }
    }


}
