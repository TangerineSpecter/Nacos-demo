package com.tangerinespecter.service;

import com.tangerinespecter.IStockService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0", group = "orange")
public class StockServiceImpl implements IStockService {

    @Override
    public String reduce(Integer productId) {
        return "成功用Dubbo减库存1";
    }
}
