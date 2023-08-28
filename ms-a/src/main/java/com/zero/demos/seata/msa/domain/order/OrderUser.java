package com.zero.demos.seata.msa.domain.order;

/**
 * @author ylxb2
 */
public class OrderUser {
    private Long id;
    private String uid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OrderUser{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                '}';
    }
}
