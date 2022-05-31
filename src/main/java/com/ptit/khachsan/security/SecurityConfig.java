package com.ptit.khachsan.security;


import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation
        .authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web
        .builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin")
                .access("hasAnyRole('ROLE_MANAGER', 'ROLE_RECEPTIONIST')")
                .antMatchers("/admin/receptionist","/admin/receptionist/*")
                .access("hasRole( 'ROLE_RECEPTIONIST')")
                .antMatchers("/admin/roomManagement","/admin/roomManagement/*")
                .access("hasRole( 'ROLE_MANAGER')")
                 .antMatchers("/admin/roomTypeManagement","/admin/roomTypeManagement/*")
                .access("hasRole( 'ROLE_MANAGER')")
                .antMatchers("/admin/serviceManagement","/admin/serviceManagement/*")
                .access("hasRole( 'ROLE_MANAGER')")
                .antMatchers("/admin/userManagement","/admin/userManagement/*")
                .access("hasRole( 'ROLE_MANAGER')")
                .antMatchers("/admin/report","/admin/report/*")
                .access("hasRole( 'ROLE_MANAGER')")
                .antMatchers("/account","/account/*")
                .access("hasAnyRole('ROLE_CUSTOMER','ROLE_MANAGER','ROLE_RECEPTIONIST')")
                .antMatchers("/booking","/booking/*")
                .access("hasRole('ROLE_CUSTOMER')")
                .antMatchers("/", "/**").access("permitAll")
              
              
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .and()
                .logout()
                  .logoutSuccessUrl("/")
                  
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")

                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("tinh");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
        
    }
   
}

