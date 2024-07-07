package com.gdsanadevlog.pcbanganalyzer.auth.config;


import com.gdsanadevlog.pcbanganalyzer.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login_form", "/register").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스에 대한 접근 허용
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login_form")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login_form")

                )
                .sessionManagement(sessionManagement -> sessionManagement
                                .maximumSessions(100)
                                .maxSessionsPreventsLogin(true)
                )
                .userDetailsService(customUserDetailService)
                .csrf(csrf ->csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}