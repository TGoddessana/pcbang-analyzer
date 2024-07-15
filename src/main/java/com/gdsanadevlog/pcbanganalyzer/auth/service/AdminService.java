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

        //password 암호화해서 넣어줘야한다.
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        // admin에서 password를 가져온 뒤 인코드를 하고, DB에 저장.
        return  adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public Admin findByName(String name){
        return adminRepository.findByName(name);
    }
}