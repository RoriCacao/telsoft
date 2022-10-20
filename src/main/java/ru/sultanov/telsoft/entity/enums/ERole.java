package ru.sultanov.telsoft.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority{
    ROLE_USER,
    ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
