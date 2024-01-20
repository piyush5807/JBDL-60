package com.example.minorproject.utils;

import com.example.minorproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Authenticate
     * Authorize
     */

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // prevention mechanism disabled
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/transaction/**").hasAuthority("std")
                .antMatchers(HttpMethod.POST, "/book/**").hasAuthority("adm")
                .antMatchers("/book/**").hasAnyAuthority("adm", "std")
//                .antMatchers(HttpMethod.POST, "/student/**").hasAuthority("adm")
                .antMatchers("/student/by-admin/**").hasAuthority("adm")
                .antMatchers(HttpMethod.POST, "/student/**").permitAll()
                .antMatchers("/student/**").hasAuthority("std")
                .and()
                .formLogin();


    }

    @Bean
    PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
