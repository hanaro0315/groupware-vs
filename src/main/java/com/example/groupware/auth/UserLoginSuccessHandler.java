package com.example.groupware.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("Login Successful");

        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
        System.out.print("권한 : ");
        for(int i = 0; i< authList.size(); i++) {
            System.out.print(authList.get(i).getAuthority() + " ");
        }

        response.sendRedirect("test");
    }
}
