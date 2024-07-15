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

    @GetMapping("/login_form") // 로그인 화면
    public String userLoginForm() {
        return "pages/auth/login_form";
    }


    @GetMapping("/register") // 회원가입 화면
    public String userRegister() {
        return "/pages/auth/register";
    }


    @PostMapping("/register") // 회원가입 화면
    public String userRegisterForm(@ModelAttribute("admin") Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/auth/register";
        }
        Admin byName = adminService.findByName(admin.getName());
        if(byName != null){
            bindingResult.rejectValue("name",null,"이미 사용중인 아이디입니다.");
            return "pages/auth/register";
        }

        adminService.registerUser(admin);
        return "redirect:/pages/auth/login_form";
    }


}