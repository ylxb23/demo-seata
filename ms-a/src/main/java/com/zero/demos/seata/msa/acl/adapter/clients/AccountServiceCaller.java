package com.zero.demos.seata.msa.acl.adapter.clients;

import com.zero.demos.seata.msa.acl.port.clients.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ylxb2
 */
@Service
public class AccountServiceCaller {
    @Autowired
    private AccountService accountService;


}
