package ru.sultanov.telsoft.entity.enums;

import org.springframework.security.core.GrantedAuthority;

//Список ролей пользователей для предоствления соответствующих прав доступа
public enum ERole implements GrantedAuthority{
    ROLE_USER,
    ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
