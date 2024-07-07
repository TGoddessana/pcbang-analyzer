package com.gdsanadevlog.pcbanganalyzer.pcbang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {
    @RequestMapping("/")
    public String index(Model model) {
        return "pages/index";
    }

    @RequestMapping("/pcbangs")
    public String pcbangs() {
        return "pages/dashboard/pcbangs/pcbang-list";
    }
}
