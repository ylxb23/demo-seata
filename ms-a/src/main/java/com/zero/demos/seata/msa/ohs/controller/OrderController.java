package com.zero.demos.seata.msa.ohs.controller;

import com.zero.demos.seata.msa.domain.order.OrderBizException;
import com.zero.demos.seata.msa.domain.order.OrderService;
import com.zero.demos.seata.msa.ohs.exceptions.FeignBizException;
import com.zero.demos.seata.msa.ohs.pl.OrderCreateRequest;
import com.zero.demos.seata.msa.ohs.pl.OrderItemResponse;
import feign.FeignException;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylxb2
 */
@RestController
@RequestMapping(path = "/order")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity<Object> getOrderById(@RequestParam(value = "id") String id) {
        OrderItemResponse orderItem = orderService.selectOrderById(id);
        if(orderItem == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(orderItem);
    }


    @GlobalTransactional(timeoutMills = 30000, name = "create_order_gtx")
    @RequestMapping(path = "/create", method = {RequestMethod.POST})
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequest request) {
        if(request == null || request.getUserId() < 1 || request.getCount() < 1 || request.getProductId() < 1 || request.getAmount() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request Param Invalid");
        }
        try {
            Long orderId = orderService.createOrder(request);
            if(orderId != null && orderId > 0) {
                return ResponseEntity.ok("Order create success, order id: " + orderId);
            }
        } catch (OrderBizException e) {
            logger.error("create order with exception, request: {}", request, e);
            throw new FeignBizException(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("unknown exception, request: {}", request, e);
            throw e;
        }
        logger.warn("Create order unknown error occur, request: {}", request);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Unknown error.");
    }
}
