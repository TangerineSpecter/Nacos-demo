package com.tangerinespecter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制
 */
@RestController
public class UserController {

    @GetMapping("/user/{userId}")
    public String create(@PathVariable Integer userId) {
        return "丢失的橘子";
    }
}
