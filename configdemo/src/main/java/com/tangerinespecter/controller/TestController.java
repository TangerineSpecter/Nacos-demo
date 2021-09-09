package com.tangerinespecter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

	@Value("${testStr}")
	private String testStr;

	@Value("${testCommon}")
	private String testCommon;

	@Value("${redisIp}")
	private String redisIp;

	@GetMapping("/test")
	public String test() {
		return testCommon + ":" + testStr + ":" + redisIp;
	}


}
