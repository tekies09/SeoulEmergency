package com.SeoulEmergency.api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;


public class RootController implements ErrorController {

    // 1. 새로고침 시 매핑 정보가 없으므로, "/error"를 리턴
    @Override
    public String getErrorPath() {
        return "/error";
    }

    // 2. "/error"를 리턴한 경우 "index.html"로 리다이렉트
    @GetMapping("/error")
    public String redirectRoot() {
        return "index.html";
    }

}
