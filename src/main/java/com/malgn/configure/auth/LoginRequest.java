package com.malgn.configure.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Schema(description = "아이디", example = "testuser")
    private String username;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    public LoginRequest() {}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
