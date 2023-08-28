package com.zero.demos.seata.msa.ohs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ylxb2
 */
@RestController
@RequestMapping(path = "/health")
public class HealthCheckController {


    @RequestMapping(path = "/check", method = {RequestMethod.GET})
    public ResponseEntity<Object> checkHealth() {
        return ResponseEntity.ok("ok");
    }
}
