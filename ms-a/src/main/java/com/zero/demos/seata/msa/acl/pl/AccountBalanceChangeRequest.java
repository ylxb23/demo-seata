package com.zero.demos.seata.msa.acl.pl;

/**
 * @author ylxb2
 */
public class AccountBalanceChangeRequest {
    private long id;
    private int amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
