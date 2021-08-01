package com.tangerinespecter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存控制
 */
@RestController
public class StockController {

    @GetMapping("/stock/reduce/{productId}")
    public String reduce(@PathVariable Integer productId) {
        return "减库存1个成功";
    }
}
