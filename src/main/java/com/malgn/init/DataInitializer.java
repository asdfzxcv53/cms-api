package com.malgn.init;

import com.malgn.content.Content;
import com.malgn.content.ContentRepository;
import com.malgn.members.Member;
import com.malgn.members.MemberRepository;
import com.malgn.members.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Member admin = Member.builder()
                .name("adminUser")
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .createdDate(LocalDateTime.now())
                .role(Role.ADMIN)
                .build();

        Member user = Member.builder()
                .name("normalUser")
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .createdDate(LocalDateTime.now())
                .role(Role.USER)
                .build();

        memberRepository.save(admin);
        memberRepository.save(user);

        // normal user 가 20개의 컨텐츠 생성
        for(int i = 1; i <= 20; i++){
            Content content = new Content(
                    "title_" + i,
                    "description_" + i,
                    "user"
            );

            contentRepository.save(content);
        }
    }
}
