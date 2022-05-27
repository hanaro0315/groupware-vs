package com.example.groupware.auth;

import com.example.groupware.service.GroupDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//해당 annotation 을 적용하는 것 만으로 filter chain 에 등록이 가능
//@EnableWebSecurity
public class SecurityConfig2 extends WebSecurityConfigurerAdapter{

    @Autowired
    private GroupDetailService groupDetailService;

    //규칙 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception{
         http.authorizeRequests()
         .antMatchers("/admin").hasRole("ADMIN")
         .antMatchers("/index").permitAll()
         .antMatchers("/home").permitAll()
         .antMatchers("/signUp").permitAll()
         .antMatchers("/login").permitAll()
         .and()
          .csrf().disable()
                 .formLogin()
                 .loginPage("/login")
                 .loginProcessingUrl("/doLogin")
                 .usernameParameter("id")
                 .passwordParameter("password")
                 .defaultSuccessUrl("/home");
    }

    //정적자원에 대해선 Security 설정을 적용하지 않음.
    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    //groupService : UserDetails 를 상속받은 객체, 로그인 된 사용자의 데이터 관리
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(groupDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
