package com.example.groupware.service;

import javax.transaction.Transactional;

import com.example.groupware.entity.GroupDetail;
import com.example.groupware.entity.User;
import com.example.groupware.repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//db에 접근 가능한 메소드 사용.
@Service
public class GroupDetailService implements UserDetailsService{

    @Autowired
    private GroupRepository repository;

    //에러가 생겨서 데이터가 빵구나도 원자성을 유지 시키기 위한 annotaion
    @Transactional
    public void joinUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findUserByEmail(email);
        return new GroupDetail(user);
    }
    
}
