package com.zero.demos.seata.msa.ohs.controller;

import com.zero.demos.seata.msa.domain.order.OrderService;
import com.zero.demos.seata.msa.ohs.pl.OrderItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylxb2
 */
@RestController
@RequestMapping(path = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/{id}", method = {RequestMethod.GET})
    public ResponseEntity<Object> getOrderById(@PathVariable(value = "id") String id) {
        OrderItemResponse orderItem = orderService.selectOrderById(id);
        if(orderItem == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(orderItem);
    }
}
