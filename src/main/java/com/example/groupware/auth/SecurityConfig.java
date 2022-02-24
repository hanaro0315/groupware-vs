package com.example.groupware.auth;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfig extends WebSecurityConfigurerAdapter{
 
    //규칙 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // http.authorizeRequests()
        // .antMatchers("/userAccess").hasRole("USER")
        // .antMatchers("/signUp").anonymous()
        // .and()
        // .formLogin()
        // .and()
        // .csrf().disable();	
    }


    //groupService : UserDetails 를 상속받은 객체, 로그인 된 사용자의 데이터 관리
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        // auth.userDetailsService(groupService).passwordEncoder(new BCryptPasswordEncoder()); 
    }
}