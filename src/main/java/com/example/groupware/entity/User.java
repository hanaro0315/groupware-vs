package com.example.groupware.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    
    @Id
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
    
}
