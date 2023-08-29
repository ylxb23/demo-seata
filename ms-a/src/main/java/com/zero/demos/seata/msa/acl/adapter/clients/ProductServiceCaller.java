package com.zero.demos.seata.msa.acl.adapter.clients;

import com.zero.demos.seata.msa.acl.pl.ProductItem;
import com.zero.demos.seata.msa.acl.pl.ProductStockChangeRequest;
import com.zero.demos.seata.msa.acl.port.clients.ProductService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 防止Feign对非成功状态码抛出异常
 * @author ylxb2
 */
@Service
public class ProductServiceCaller {
    @Autowired
    private ProductService productService;



    public ResponseEntity<ProductItem> getById(Long id) {
        try {
            return productService.getById(id);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> deductionProductStock(ProductStockChangeRequest productStockChangeRequest) {
        try {
            return productService.deductionProductStock(productStockChangeRequest);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.responseBody().get().toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
