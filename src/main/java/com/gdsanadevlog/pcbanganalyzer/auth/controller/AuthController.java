package com.gdsanadevlog.pcbanganalyzer.auth.controller;

import com.gdsanadevlog.pcbanganalyzer.auth.domain.Admin;
import com.gdsanadevlog.pcbanganalyzer.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;

    @GetMapping("/login")
    public String userregform(){
        return "pages/auth/login";
    }


    @GetMapping("/index")
    public String welcome(){
        return "pages/index";
    }

}