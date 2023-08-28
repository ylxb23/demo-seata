package com.zero.demos.seata.msc.domain;

import com.zero.demos.seata.msc.acl.repository.entity.ProductEntity;

/**
 * @author ylxb2
 */
public class Product {
    private Long id;

    private String name;

    private Integer price;

    private Integer count;

    private Integer createTime;

    private Integer updateTime;

    public static Product from(ProductEntity entity) {
        if(entity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        product.setCount(entity.getStock());
        product.setCreateTime(entity.getCreateTime());
        product.setUpdateTime(entity.getUpdateTime());
        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
