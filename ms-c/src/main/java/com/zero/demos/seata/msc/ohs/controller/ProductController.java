package com.zero.demos.seata.msc.ohs.controller;

import com.zero.demos.seata.msc.domain.ProductService;
import com.zero.demos.seata.msc.ohs.pl.ProductItemResponse;
import com.zero.demos.seata.msc.ohs.pl.ProductStockChangeRequest;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ylxb2
 */
@RestController
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/product", method = {RequestMethod.GET})
    public ResponseEntity<ProductItemResponse> getById(@RequestParam(value = "id") Long id){
        ProductItemResponse res = productService.getById(id);
        if(res == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(res);
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/product/stock/deduction", method = {RequestMethod.POST})
    public ResponseEntity<String> deductionProductStock(@RequestBody ProductStockChangeRequest request) {
        logger.info("product deduction stock with global transaction, request: {}, xid: {}", request, RootContext.getXID());
        // 传入的数量是正数，强制转为负数
        if(request == null || request.getId() < 0 || request.getCount() < 0) {
            logger.warn("product deduction stock with invalid request param, request: {}", request);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request param invalid");
        }
        request.setCount(-request.getCount());
        int res = productService.changeStock(request);
        if(res < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("stock deduction failure");
        }
        return ResponseEntity.ok("deduction product stock success, after that stock is: " + res);
    }

}
