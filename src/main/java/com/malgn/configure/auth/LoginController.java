package com.malgn.configure.auth;

import com.malgn.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }


    @Operation(
            summary = "로그인",
            description = "username 과 password 로 로그인 후 access token 발급"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                     "accessToken": "eyJhbGciOiJIUzI1NiJ9..."
                                    }
                                    """
                            )

                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "아이디 또는 비밀번호 오류",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "code": "...",
                                            "message": "..."
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }
}
