package com.zero.demos.seata.msa.ohs.pl;

import com.zero.demos.seata.msa.domain.order.Order;
import com.zero.demos.seata.msa.domain.order.OrderProduct;
import com.zero.demos.seata.msa.domain.order.OrderStatusEnum;
import com.zero.demos.seata.msa.domain.order.OrderUser;

/**
 * @author ylxb2
 */
public class OrderCreateRequest {
    private long userId;
    private long productId;
    private int count;
    private int amount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Order to() {
        Order order = new Order();
        order.setAmount(this.getAmount());
        order.setStatus(OrderStatusEnum.CREATED);
        order.setCreateTime((int)(System.currentTimeMillis() / 1000));
        order.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        OrderUser user = new OrderUser();
        user.setId(this.getUserId());
        order.setUser(user);
        OrderProduct product = new OrderProduct();
        product.setId(this.getProductId());
        product.setCount(this.getCount());
        order.setProduct(product);
        return order;
    }
}
