package com.example.groupware.controller;

import com.example.groupware.entity.GroupDetail;
import com.example.groupware.entity.User;
import com.example.groupware.service.GroupDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
public class LoginController2 {

    @Autowired
    private GroupDetailService service;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/signUp")
    public String signUp(String email, String password, String id) {

        User user = new User();
        user.setRole("USER");
        user.setPassword(password);
        user.setEmail(email);
        user.setId(id);

        service.joinUser(user);
        return "redirect:/login";
    }

    @GetMapping("/userAccess")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        GroupDetail userDetail = (GroupDetail)authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", userDetail.getUsername());      //유저 이메일
    	return "user_access";
    }


    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

}