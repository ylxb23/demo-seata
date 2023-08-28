package com.zero.demos.seata.msa.domain.order;

import com.zero.demos.seata.msa.acl.pl.AccountItem;
import com.zero.demos.seata.msa.acl.pl.ProductItem;
import com.zero.demos.seata.msa.acl.port.clients.AccountService;
import com.zero.demos.seata.msa.acl.port.clients.ProductService;
import com.zero.demos.seata.msa.acl.repository.OrderRepository;
import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import com.zero.demos.seata.msa.ohs.pl.OrderItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author ylxb2
 */
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;

    public OrderItemResponse selectOrderById(String id) {
        Long orderId = Long.parseLong(id);
        OrderEntity entity = orderRepository.selectById(orderId);
        if(entity == null) {
            logger.info("order id:{}, order not exists.", id);
            return null;
        }
        Order order = Order.from(entity);
        if(order != null) {
            fillUser(order);
            fillProduct(order);
        }
        return OrderItemResponse.from(order);
    }

    private void fillProduct(Order order) {
        OrderProduct product = order.getProduct();
        if(product == null || product.getId() == null) {
            logger.warn("order with empty product info, order: {}", order);
            return;
        }
        Long id = product.getId();
        ResponseEntity<ProductItem> res = productService.getById(id);
        if(res != null && res.getStatusCode().is2xxSuccessful()) {
            ProductItem productItem = res.getBody();
            if(productItem != null) {
                product.setName(productItem.getName());
                product.setPrice(productItem.getPrice());
            }
        }
    }

    private void fillUser(Order order) {
        OrderUser user = order.getUser();
        if(user == null || user.getId() == null) {
            logger.warn("order with empty user info, order: {}", order);
            return;
        }
        Long id = user.getId();
        ResponseEntity<AccountItem> res = accountService.getById(id);
        if(res.getStatusCode().is2xxSuccessful()) {
            AccountItem account = res.getBody();
            if(account != null) {
                user.setUid(account.getUserId());
            }
        }
    }
}
