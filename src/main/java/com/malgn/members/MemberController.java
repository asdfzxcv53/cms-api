package com.malgn.members;

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

    @PostMapping(value = "/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        // 회원가입

        MemberResponse memberResponse = memberService.signup(memberSignUpRequest);

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping(value = "/check-username")
    public ResponseEntity<CheckUsernameResponse> checkUsername(@RequestParam String username) {
        // 아이디 중복 확인
        // true : 중복아이디 존재x, false : 중복아이디 존재
        boolean available = memberService.checkUsername(username);
        CheckUsernameResponse checkUsernameResponse = new CheckUsernameResponse(available);

        return ResponseEntity.ok(checkUsernameResponse);
    }
}
