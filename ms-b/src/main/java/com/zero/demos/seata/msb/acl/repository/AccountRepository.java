package com.zero.demos.seata.msb.acl.repository;

import com.zero.demos.seata.msb.acl.repository.entity.AccountEntity;
import com.zero.demos.seata.msb.acl.repository.mapper.AccountMapper;
import com.zero.demos.seata.msb.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AccountMapper accountMapper;


    public AccountEntity selectById(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public Long insert(Account account) {
        if(account == null) {
            return -1L;
        }
        AccountEntity entity = new AccountEntity();
        entity.setBalance(account.getBalance());
        entity.setUserId(account.getUserId());
        int curr = (int) (System.currentTimeMillis() / 1000);
        entity.setCreateTime(curr);
        entity.setUpdateTime(curr);
        try {
            int res = accountMapper.insert(entity);
            if(res > 0) {
                return entity.getId();
            }
        } catch (Exception e) {
            logger.error("insert account entity error, account: {}", account, e);
        }
        return -1L;
    }


}
