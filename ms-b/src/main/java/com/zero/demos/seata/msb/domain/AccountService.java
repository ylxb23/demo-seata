package com.zero.demos.seata.msb.domain;

import com.zero.demos.seata.msb.acl.repository.AccountRepository;
import com.zero.demos.seata.msb.acl.repository.entity.AccountEntity;
import com.zero.demos.seata.msb.ohs.pl.AccountItemRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository accountRepository;

    public AccountItemResponse getById(Long id) {
        AccountEntity entity = accountRepository.selectById(id);
        if(entity == null) {
            return null;
        }
        AccountItemResponse res = AccountItemResponse.from(entity);
        return res;
    }

    public String addAccount(AccountItemRequest request) {
        Account account =  request.to();
        if(account == null) {
            return "invalid request";
        }
        try {
            Long id = accountRepository.insert(account);
            return "added user id is: " + id;
        } catch (Exception e) {
            logger.error("insert account error", e);
            return "add account failed, may userId exists";
        }
    }
}
