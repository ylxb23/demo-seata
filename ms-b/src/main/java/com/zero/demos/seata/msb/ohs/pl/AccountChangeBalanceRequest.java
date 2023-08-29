package com.zero.demos.seata.msb.ohs.pl;

/**
 * Deduction or Increase account balance request
 * @author ylxb2
 */
public class AccountChangeBalanceRequest {
    private Long id;
    /**
     * 变更的余额，正数则增加，负数则扣除
     */
    private int amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
