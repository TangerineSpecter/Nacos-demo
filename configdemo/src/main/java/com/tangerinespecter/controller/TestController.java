package com.tangerinespecter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${testStr}")
    private String testStr;

    @GetMapping("/test")
    public String test() {
        return testStr;
    }
}
