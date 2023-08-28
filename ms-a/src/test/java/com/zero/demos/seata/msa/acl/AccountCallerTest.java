package com.zero.demos.seata.msa.acl;


import com.zero.demos.seata.msa.TestBase;
import com.zero.demos.seata.msa.acl.pl.AccountItem;
import com.zero.demos.seata.msa.acl.port.clients.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AccountCallerTest extends TestBase {

    @Autowired
    private AccountService accountService;

    @Test
    public void testGetAccountById() {
        Long id = 1L;
        ResponseEntity<AccountItem> res = accountService.getAccountById(id);
        logger.info("res: {}", res);
        logger.info("account: {}", res.getBody());
    }
}
