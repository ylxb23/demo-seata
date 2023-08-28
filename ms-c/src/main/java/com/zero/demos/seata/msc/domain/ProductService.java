package com.zero.demos.seata.msc.domain;

import com.zero.demos.seata.msc.acl.repository.ProductRepository;
import com.zero.demos.seata.msc.acl.repository.entity.ProductEntity;
import com.zero.demos.seata.msc.ohs.pl.ProductItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ylxb2
 */
@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductRepository productRepository;


    public ProductItemResponse getById(Long id) {
        ProductEntity entity = productRepository.selectById(id);
        if(entity == null) {
            logger.warn("product not exists, id: {}", id);
            return null;
        }
        Product product = Product.from(entity);
        // any others ops
        return ProductItemResponse.from(product);
    }
}
