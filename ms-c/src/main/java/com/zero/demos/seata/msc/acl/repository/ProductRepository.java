package com.zero.demos.seata.msc.acl.repository;

import com.zero.demos.seata.msc.acl.repository.entity.ProductEntity;
import com.zero.demos.seata.msc.acl.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ylxb2
 */
@Repository
public class ProductRepository {
    @Autowired
    private ProductMapper productMapper;

    public ProductEntity selectById(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
