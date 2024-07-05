package com.gdsanadevlog.pcbanganalyzer.auth.security;




import com.gdsanadevlog.pcbanganalyzer.auth.domain.Admin;
import com.gdsanadevlog.pcbanganalyzer.auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByName(username);
        if(admin == null){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(admin.getPassword());


        return userBuilder.build();
    }
}