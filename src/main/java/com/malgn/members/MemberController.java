package com.malgn.members;

import com.malgn.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원가입",
            description = "username 과 password 로 회원가입"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                     "id": 1,
                                     "name": "song",
                                     "username": "testuser"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 아이디 입니다.",
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
    @PostMapping(value = "/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        // 회원가입

        MemberResponse memberResponse = memberService.signup(memberSignUpRequest);

        return ResponseEntity.ok(memberResponse);
    }

    @Operation(
            summary = "아이디 중복확인",
            description = "username 으로 중복확인"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "true 면 아이디 생성 가능, false 면 아이디 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckUsernameResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                     "available": "true / false"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 아이디 입니다.",
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
    @GetMapping(value = "/check-username")
    public ResponseEntity<CheckUsernameResponse> checkUsername(@RequestParam String username) {
        // 아이디 중복 확인
        // true : 중복아이디 존재x, false : 중복아이디 존재
        boolean available = memberService.checkUsername(username);
        CheckUsernameResponse checkUsernameResponse = new CheckUsernameResponse(available);

        return ResponseEntity.ok(checkUsernameResponse);
    }
}
