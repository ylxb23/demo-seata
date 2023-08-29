package com.zero.demos.seata.msa.domain.order;

/**
 * @author ylxb2
 */
public class OrderBizException extends RuntimeException {

    public OrderBizException(String msg) {
        super(msg);
    }
}
