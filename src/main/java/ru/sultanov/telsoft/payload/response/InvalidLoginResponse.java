package ru.sultanov.telsoft.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
