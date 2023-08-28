package com.zero.demos.seata.msa.domain.order;

import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * @author ylxb2
 */
public class Order {
    private Long id;
    private OrderUser user;
    private OrderProduct product;
    private Integer amount;
    private OrderStatusEnum status;
    private Integer createTime;
    private Integer updateTime;

    public static Order from(OrderEntity entity) {
        if(entity == null) {
            return null;
        }
        Order order = new Order();
        order.setId(entity.getId());
        OrderUser user = new OrderUser();
        user.setId(Long.parseLong(entity.getUserId()));
        order.setUser(user);
        OrderProduct product = new OrderProduct();
        product.setId(entity.getProductId());
        product.setCount(entity.getCount());
        order.setProduct(product);
        order.setAmount(entity.getPrice());
        order.setStatus(OrderStatusEnum.of(entity.getStatus()));
        order.setCreateTime(entity.getCreateTime());
        order.setUpdateTime(entity.getUpdateTime());
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderUser getUser() {
        return user;
    }

    public void setUser(OrderUser user) {
        this.user = user;
    }

    public OrderProduct getProduct() {
        return product;
    }

    public void setProduct(OrderProduct product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public void fillUser() {
        // TODO
    }

    public void fillProduct() {
        // TODO
    }
}
