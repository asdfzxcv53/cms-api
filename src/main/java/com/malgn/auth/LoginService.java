package com.malgn.auth;

import com.malgn.configure.security.JwtUtil;
import com.malgn.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // 비밀번호 확인
            if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
            }

            String accessToken = jwtUtil.generateAccessToken(userDetails);
            return new LoginResponse(accessToken);
        } catch(UsernameNotFoundException e) {
            // 예외 통일을 위한 처리
            throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}
