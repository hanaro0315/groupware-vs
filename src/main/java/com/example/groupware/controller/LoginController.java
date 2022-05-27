package com.example.groupware.controller;

import com.example.groupware.entity.GroupDetail;
import com.example.groupware.entity.User;
import com.example.groupware.service.GroupDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController{

    @Autowired
    private GroupDetailService service;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin2")
    public String admin2() {
        return "admin2";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    @GetMapping("/access_denied")
    public String access_denied() {
        System.out.println("access denied");
        return "admin";
    }

    @PostMapping("/signUp")
    public String signUp(String email, String password, String id, String auth) {

        System.out.println("email"+ email);
        System.out.println("password"+ password);
        System.out.println("id"+ id);
        System.out.println("auth"+ auth);

        User user = new User();
        user.setRole(auth);
        user.setPassword(password);
        user.setEmail(email);
        user.setId(id);

        service.joinUser(user);
        return "redirect:/login";
    }

    @GetMapping("test")
    public String test(Model model, Authentication authentication)
    {
        GroupDetail groupDetail = (GroupDetail) authentication.getPrincipal();

        model.addAttribute("id", "id : "+groupDetail.getUsername());
        model.addAttribute("auth", "auth : "+authentication.getAuthorities());

        return "test";
    }
}