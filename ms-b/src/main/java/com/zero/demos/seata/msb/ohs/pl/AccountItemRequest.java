package com.zero.demos.seata.msb.ohs.pl;

import com.zero.demos.seata.msb.domain.Account;
import org.springframework.util.StringUtils;

public class AccountItemRequest {
    private String userId;
    private Integer balance;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Account to() {
        if(!StringUtils.hasText(this.getUserId()) || this.getBalance() < 0) {
            return null;
        }
        Account account = new Account();
        account.setUserId(this.getUserId());
        account.setBalance(this.getBalance());
        return account;
    }
}
