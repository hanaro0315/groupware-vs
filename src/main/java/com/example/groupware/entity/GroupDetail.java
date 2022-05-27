package com.example.groupware.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//User 정보를 담아서 계정 권한이 있는 정보 객체로 버무림.
//로그인 시 id를 입력하면 받은 id를 GroupDetails을 통해 비교
public class GroupDetail implements UserDetails{

    private static final String ROLE_PREFIX = "ROLE_";
    private User user;

    private String id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public GroupDetail(User user, Collection<? extends GrantedAuthority> authorities){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    //계정이 가지고 있는 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities = authorities;
    }

    //계정 pw 반환
    @Override
    public String getPassword() {
        return this.password;
    }

    //계정 이름 반환
    @Override
    public String getUsername() {
        return this.id;
    }

    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
