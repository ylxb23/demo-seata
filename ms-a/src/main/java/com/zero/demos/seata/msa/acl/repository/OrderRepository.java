package com.zero.demos.seata.msa.acl.repository;

import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import com.zero.demos.seata.msa.acl.repository.mapper.OrderMapper;
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
}
