package com.xian.currency_exchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping("main")
    public String index() {
        return "main";
    }
}
