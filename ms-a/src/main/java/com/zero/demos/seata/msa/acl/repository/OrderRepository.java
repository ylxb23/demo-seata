package com.zero.demos.seata.msa.acl.repository;

import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import com.zero.demos.seata.msa.acl.repository.mapper.OrderMapper;
import com.zero.demos.seata.msa.domain.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ylxb2
 */
@Repository
public class OrderRepository {
    @Autowired
    private OrderMapper orderMapper;

    public OrderEntity selectById(Long orderId) {
        OrderEntity order = orderMapper.selectByPrimaryKey(orderId);
        return order;
    }

    public OrderEntity createOrder(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setUserId(""+order.getUser().getId());
        entity.setProductId(order.getProduct().getId());
        entity.setPrice(order.getAmount());
        entity.setCount(order.getProduct().getCount());
        entity.setCreateTime(order.getCreateTime());
        entity.setUpdateTime(order.getUpdateTime());
        entity.setStatus(order.getStatus().getStatus());
        int res = orderMapper.insertSelective(entity);
        if(res > 0) {
            return entity;
        }
        return null;
    }
}
