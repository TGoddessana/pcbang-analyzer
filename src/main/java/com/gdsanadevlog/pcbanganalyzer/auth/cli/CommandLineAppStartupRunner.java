package com.gdsanadevlog.pcbanganalyzer.auth.cli;


import com.gdsanadevlog.pcbanganalyzer.auth.domain.Admin;
import com.gdsanadevlog.pcbanganalyzer.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final AdminService adminService;

    @Override
    public void run(String...args) throws Exception {
        if(adminService.findByName("admin") == null) {
            adminService.registerUser(Admin.builder()
                    .name("admin")
                    .password("admin")
                    .build());
        }

    }
}