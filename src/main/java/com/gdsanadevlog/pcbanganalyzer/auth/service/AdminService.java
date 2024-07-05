package com.gdsanadevlog.pcbanganalyzer.auth.service;

import com.gdsanadevlog.pcbanganalyzer.auth.domain.Admin;
import com.gdsanadevlog.pcbanganalyzer.auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public Admin registerUser(Admin admin){
        //role 추가

        //password 암호화해서 넣어줘야한다.
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return  adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public Admin findByUsername(String username){
        return adminRepository.findByName(username);
    }
}