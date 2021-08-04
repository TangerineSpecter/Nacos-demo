package com.tangerspecter.controller;

import com.tangerinespecter.IStockService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 订单控制
 */
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Reference
    private IStockService stockService;

    @GetMapping("/order/create_v1")
    public String createOrderV1(Integer productId, Integer userId) {
        String productName = restTemplate.getForObject("http://localhost:9000/product/" + productId, String.class);
        String userName = restTemplate.getForObject("http://localhost:10000/user/" + userId, String.class);
        String result = restTemplate.getForObject("http://localhost:11000/stock/reduce/" + productId, String.class);
        String shopCartResult = restTemplate.getForObject("http://localhost:12000/shopcart/remove?productId" + productId + "&userId=" + userId, String.class);
        return "用户：" + userName + "购买商品：" + productName + result + shopCartResult;
    }

    @GetMapping("/order/create_v2")
    public String createOrderV2(Integer productId, Integer userId) {
        //通过stock-sever服务名进行接口调用，可以做负载均衡
        String result = restTemplate.getForObject("http://stock-sever/stock/reduce/" + productId, String.class);
        return result;
    }

    @GetMapping("/order/create_v3")
    public String createOrderV3(Integer productId, Integer userId) {
        //通过dubbo远端RPC接口调用
        String result = stockService.reduce(productId);
        return result;
    }
}
