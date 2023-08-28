package com.zero.demos.seata.msa.ohs.pl;


import com.zero.demos.seata.msa.acl.repository.entity.OrderEntity;
import com.zero.demos.seata.msa.domain.order.Order;
import com.zero.demos.seata.msa.domain.order.OrderProduct;
import com.zero.demos.seata.msa.domain.order.OrderStatusEnum;
import com.zero.demos.seata.msa.domain.order.OrderUser;
import com.zero.demos.seata.msa.utils.DateUtil;

import java.util.Date;

/**
 * @author ylxb2
 */
public class OrderItemResponse {
    private Long id;
    private UserItemResponse user;
    private ProductItemResponse product;
    private Integer amount;
    private String status;
    private String createTime;
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserItemResponse getUser() {
        return user;
    }

    public void setUser(UserItemResponse user) {
        this.user = user;
    }

    public ProductItemResponse getProduct() {
        return product;
    }

    public void setProduct(ProductItemResponse product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static OrderItemResponse from(Order order) {
        if(order == null) {
            return null;
        }
        OrderItemResponse res = new OrderItemResponse();
        res.setId(order.getId());
        res.setUser(UserItemResponse.from(order.getUser()));
        res.setProduct(ProductItemResponse.from(order.getProduct()));
        res.setAmount(order.getAmount());
        res.setStatus(order.getStatus().name());
        res.setCreateTime(DateUtil.format(new Date(order.getCreateTime() * 1000L)));
        res.setUpdateTime(DateUtil.format(new Date(order.getUpdateTime() * 1000L)));
        return res;
    }

    public static class UserItemResponse {
        private long id;
        private String userId;

        public static UserItemResponse from(OrderUser user) {
            if(user == null) {
                return null;
            }
            UserItemResponse res = new UserItemResponse();
            res.setId(user.getId());
            res.setUserId(user.getUid());
            return res;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }

    public static class ProductItemResponse {
        private long id;
        private String name;
        private Integer price;
        private Integer count;

        public static ProductItemResponse from(OrderProduct product) {
            if(product == null) {
                return null;
            }
            ProductItemResponse res = new ProductItemResponse();
            res.setId(product.getId());
            res.setPrice(product.getPrice());
            res.setName(product.getName());
            res.setCount(product.getCount());
            return res;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
