package com.gdsanadevlog.pcbanganalyzer.auth.controller;


import com.gdsanadevlog.pcbanganalyzer.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;

    @GetMapping("/login")
    public String userLoginForm() {
        return "pages/auth/login";
    }

    @GetMapping("/register")
    public String userRegisterForm() {
        return "pages/auth/register";
    }

    @PostMapping("/register")
    public String userRegister() {
        return "redirect:/login";
    }
}