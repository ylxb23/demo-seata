package com.zero.demos.seata.msa.domain.order;

/**
 * @author ylxb2
 */

public enum OrderStatusEnum {
    /**
     * 创建
     */
    CREATED((byte)0),
    /**
     * 已支付
     */
    PAID((byte)1),
    /**
     * 已完成
     */
    FINISHED((byte)2),
    /**
     * 已取消
     */
    CANCELED((byte)3),
    /**
     * 已删除
     */
    DELETED((byte)-1);

    private final Byte status;

    OrderStatusEnum(Byte s) {
        this.status = s;
    }

    public Byte getStatus() {
        return status;
    }

    public static OrderStatusEnum of(Byte s) {
        if(s == null) {
            throw new NullPointerException("status can not be null");
        }
        OrderStatusEnum[] values = values();
        for(OrderStatusEnum item : values) {
            if(item.getStatus().byteValue() == s.byteValue()) {
                return item;
            }
        }
        throw new IllegalArgumentException("status is invalid");
    }
}
