package com.zero.demos.seata.msc.ohs.pl;

import com.zero.demos.seata.msc.domain.Product;
import com.zero.demos.seata.msc.utils.DateUtil;

import java.util.Date;

/**
 * @author ylxb2
 */
public class ProductItemResponse {
    private Long id;

    private String name;

    private Integer price;

    private Integer count;

    private String createTime;

    private String updateTime;
    public static ProductItemResponse from(Product product) {
        if(product == null) {
            return null;
        }
        ProductItemResponse res = new ProductItemResponse();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setPrice(product.getPrice());
        res.setCount(product.getCount());
        if(product.getCreateTime() != null) {
            res.setCreateTime(DateUtil.format(new Date(product.getCreateTime()*1000L), DateUtil.DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss));
        }
        if(product.getUpdateTime() != null) {
            res.setUpdateTime(DateUtil.format(new Date(product.getUpdateTime()*1000L), DateUtil.DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss));
        }
        return res;
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
}
