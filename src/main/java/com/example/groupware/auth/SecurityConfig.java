package com.example.groupware.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.List;
import java.util.ArrayList;


//해당 annotation 을 적용하는 것 만으로 filter chain 에 등록이 가능
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private FormAuthenticationProvider formAuthenticationProvider;

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    //규칙 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
         http.authorizeRequests()
         .antMatchers("/user").hasRole("USER")
                 .antMatchers("/admin2").hasRole("ADMIN")
                 .accessDecisionManager(accessDecisionManager())
         .antMatchers("/signUp").permitAll()
         .and()
          .csrf().disable()
                 .formLogin()
                    .loginPage("/admin")
                    .loginProcessingUrl("/loginProcess")
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .failureUrl("/signUp")
                    .successHandler(userLoginSuccessHandler)
                    .failureHandler(loginFailHandler)
                 .and()
                 .exceptionHandling()
                 .accessDeniedPage("/access_denied")
                 .and()
                 .logout()
                 .logoutUrl("/dologout")
                 .logoutSuccessHandler(logoutSuccessHandler);
    }

    //정적자원에 대해선 Security 설정을 적용하지 않음.
    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    //인증로직을 담당하는 Provider 등록
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(formAuthenticationProvider);
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

    @Bean
    public AccessDecisionManager accessDecisionManager(){

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        //웹 시큐리티에서 ROLE_xxx가 매치하는지 확인
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
        voters.add(webExpressionVoter);
        return new AffirmativeBased(voters);
    }
}
