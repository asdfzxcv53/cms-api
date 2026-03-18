package com.malgn.members;

import com.malgn.exception.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse signup(MemberSignUpRequest memberSignUpRequest) {
        // 회원가입

        // id 중복인 경우
        if(memberRepository.existsByUsername(memberSignUpRequest.getUsername())) {
            throw new DuplicateUsernameException();
        }

        String encodedPassword = passwordEncoder.encode(memberSignUpRequest.getPassword());

        Member member = Member.builder()
                .name(memberSignUpRequest.getName())
                .username(memberSignUpRequest.getUsername())
                .password(encodedPassword)
                .createdDate(LocalDateTime.now())
                .build();

        Member savedMember = memberRepository.save(member);

        return new MemberResponse(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getUsername()
        );
    }

    public boolean checkUsername(String username) {
        return !memberRepository.existsByUsername(username);
    }
}
