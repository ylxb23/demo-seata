package com.zero.demos.seata.msc.ohs.controller;

import com.zero.demos.seata.msc.domain.ProductService;
import com.zero.demos.seata.msc.ohs.pl.ProductItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
