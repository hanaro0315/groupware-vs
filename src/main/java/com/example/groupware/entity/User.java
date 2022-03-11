package com.example.groupware.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sampleTable")
public class User {
    
    @Id
    @Column(name="member_id")
    private String id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

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
        this.role = "ROLE_"+role;
    }

    public String getRole(){
        return this.role;
    }
    
}
