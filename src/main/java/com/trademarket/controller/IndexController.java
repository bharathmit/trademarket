package com.trademarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	
	
	@RequestMapping
    public String getIndexPage() {
        return "index";
    }
	
	@RequestMapping("login/layout")
    public String getLoginPage() {
        return "partials/login";
    }
	
}
