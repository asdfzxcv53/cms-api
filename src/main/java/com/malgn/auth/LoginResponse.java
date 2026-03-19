package com.malgn.auth;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
