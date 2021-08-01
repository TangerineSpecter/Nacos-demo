package com.tangerinespecter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车控制
 */
@RestController
public class ShopCartController {

    @GetMapping("/shopcart/remove")
    public String remove(Integer productId, Integer userId) {
        return "移除购物车成功";
    }
}
