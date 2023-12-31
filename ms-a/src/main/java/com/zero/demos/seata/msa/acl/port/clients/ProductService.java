package com.zero.demos.seata.msa.acl.port.clients;

import com.zero.demos.seata.msa.acl.pl.ProductItem;
import com.zero.demos.seata.msa.acl.pl.ProductStockChangeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ylxb2
 */
@FeignClient(name = "microserviceC", contextId = "microserviceC")
public interface ProductService {

    @RequestMapping(path = "/product", method = {RequestMethod.GET})
    ResponseEntity<ProductItem> getById(@RequestParam("id") Long id);

    @RequestMapping(path="/product/stock/deduction", method = {RequestMethod.POST})
    ResponseEntity<String> deductionProductStock(@RequestBody ProductStockChangeRequest productStockChangeRequest);
}
