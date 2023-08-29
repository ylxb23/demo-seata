package com.zero.demos.seata.msc.domain;

import com.zero.demos.seata.msc.acl.repository.ProductRepository;
import com.zero.demos.seata.msc.acl.repository.entity.ProductEntity;
import com.zero.demos.seata.msc.ohs.pl.ProductItemResponse;
import com.zero.demos.seata.msc.ohs.pl.ProductStockChangeRequest;
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

    /**
     * 返回变更后的库存数量
     * @param request
     * @return
     */
    public int changeStock(ProductStockChangeRequest request) {
        int res = productRepository.updateProductStock(request.getId(), request.getCount());
        if(res < 1) {
            logger.warn("update product stock failure, request: {}", request);
            return -1;
        }
        ProductEntity entity = productRepository.selectById(request.getId());
        if(entity != null) {
            return entity.getStock();
        }
        return -1;
    }
}
