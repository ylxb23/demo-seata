package com.zero.demos.seata.msa.domain.order;

import com.zero.demos.seata.msa.acl.adapter.clients.AccountServiceCaller;
import com.zero.demos.seata.msa.acl.adapter.clients.ProductServiceCaller;
import com.zero.demos.seata.msa.acl.pl.AccountBalanceChangeRequest;
import com.zero.demos.seata.msa.acl.pl.AccountItem;
import com.zero.demos.seata.msa.acl.pl.ProductItem;
import com.zero.demos.seata.msa.acl.pl.ProductStockChangeRequest;
import com.zero.demos.seata.msa.acl.repository.OrderRepository;
import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import com.zero.demos.seata.msa.ohs.pl.OrderCreateRequest;
import com.zero.demos.seata.msa.ohs.pl.OrderItemResponse;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ylxb2
 */
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountServiceCaller accountServiceCaller;
    @Autowired
    private ProductServiceCaller productServiceCaller;

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
        ResponseEntity<ProductItem> res = productServiceCaller.getById(id);
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
        ResponseEntity<AccountItem> res = accountServiceCaller.getById(id);
        if(res.getStatusCode().is2xxSuccessful()) {
            AccountItem account = res.getBody();
            if(account != null) {
                user.setUid(account.getUserId());
            }
        }
    }

    public Long createOrder(@RequestBody OrderCreateRequest request) {
        String xid = RootContext.getXID();
        logger.info("create order with global transaction, request: {}, xid: {}", request, xid);
        // 检查订单金额是否正确: 查询产品信息，核对产品价格和数量的乘积是否等于订单金额
        // 检查商品库存是否够用
        ProductItem product = null;
        ResponseEntity<ProductItem> productRes = productServiceCaller.getById(request.getProductId());
        // 不校验金额和库存，在后续的子事务中有校验，去除该校验方便事务的可观测
///        if(productRes != null && productRes.getStatusCode().is2xxSuccessful()) {
//            product = productRes.getBody();
//            if(request.getAmount() != product.getPrice() * request.getCount()) {
//                throw new OrderBizException("order amount invalid[xid=" + xid + "], should be " + product.getPrice() * request.getCount());
//            }
//            if(product.getCount() < request.getCount()) {
//                throw new OrderBizException("product storage not enough[xid=" + xid + "]");
//            }
//        } else {
//            throw new OrderBizException("check order amount failure[xid=" + xid + "], get product info fail, request: " + request);
//        }
        // 扣除用户余额，NOTE:余额扣除和校验一起做了，就不在前面做余额校验了
        AccountBalanceChangeRequest accountBalanceChangeRequest = new AccountBalanceChangeRequest();
        accountBalanceChangeRequest.setId(request.getUserId());
        accountBalanceChangeRequest.setAmount(request.getAmount());
        ResponseEntity<String> accountChangeRes = accountServiceCaller.deductionBalance(accountBalanceChangeRequest);
        if(accountChangeRes == null || !accountChangeRes.getStatusCode().is2xxSuccessful()) {
            logger.warn("deduction account balance failure, request: {}", request);
            throw new OrderBizException("deduction account balance failure, [xid=" + xid + "]" + accountChangeRes != null ? accountChangeRes.getBody():"");
        }
        // 扣减商品库存
        ProductStockChangeRequest productStockChangeRequest = new ProductStockChangeRequest();
        productStockChangeRequest.setId(request.getProductId());
        productStockChangeRequest.setCount(request.getCount());
        ResponseEntity<String> stockChangeRes = productServiceCaller.deductionProductStock(productStockChangeRequest);
        if(stockChangeRes == null || !stockChangeRes.getStatusCode().is2xxSuccessful()) {
            logger.warn("deduction product stock failure[xid={}], request: {}", xid, request);
            throw new OrderBizException("deduction product stock failure[xid="+xid+"]");
        }
        // 创建订单，并获取订单编号
        Order order = request.to();
        OrderEntity orderEntity = orderRepository.createOrder(order);
        logger.info("Create order success, request: {}, product[id={}] stock has: {}, user[id={}] account balance has: {}", request, request.getProductId(), productRes.getBody().getCount(), request.getUserId(), accountChangeRes.getBody());
        if(orderEntity != null && orderEntity.getId() != null) {
            return orderEntity.getId();
        }
        return -1L;
    }
}
