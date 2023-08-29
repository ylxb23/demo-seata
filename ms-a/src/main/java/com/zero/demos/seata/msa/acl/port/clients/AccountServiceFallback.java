package com.zero.demos.seata.msa.acl.port.clients;

import com.zero.demos.seata.msa.acl.pl.AccountBalanceChangeRequest;
import com.zero.demos.seata.msa.acl.pl.AccountItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author ylxb2
 */
public class AccountServiceFallback implements AccountService {
    @Override
    public ResponseEntity<AccountItem> getById(Long id) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    @Override
    public ResponseEntity<String> accAccount(AccountItem account) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    @Override
    public ResponseEntity<String> deductionBalance(AccountBalanceChangeRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
}
