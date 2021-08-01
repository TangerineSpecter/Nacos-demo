package com.tangerinespecter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品控制
 */
@RestController
public class ProductionController {

    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable Integer productId) {
        return "IPHONE 12";
    }
}
