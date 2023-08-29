package com.zero.demos.seata.msa.acl.pl;

/**
 * @author ylxb2
 */
public class ProductStockChangeRequest {
    private long id;
    private int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
