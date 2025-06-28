package com.tshewang.todo.entity;

import jakarta.persistence.Embeddable;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
public class Authority implements GrantedAuthority {
    private String authority;

    // default constructor
    public Authority(){
    }
    public Authority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return "";
    }
}
