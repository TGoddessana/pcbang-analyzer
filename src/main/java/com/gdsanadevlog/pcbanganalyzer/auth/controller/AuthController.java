package com.gdsanadevlog.pcbanganalyzer.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdsanadevlog.pcbanganalyzer.auth.service.LoginService;
import com.gdsanadevlog.pcbanganalyzer.auth.service.LogoutService;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;
    private final LogoutService logoutService;

    @GetMapping("/login")
    public String login() {
        // 로그인 폼을 보여주는 뷰
        return "pages/auth/login";
    }

    @PostMapping("/login")
    public String loginPost() {
        // 로그인 처리
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        // 로그아웃 처리
        return "redirect:/";
    }
}
