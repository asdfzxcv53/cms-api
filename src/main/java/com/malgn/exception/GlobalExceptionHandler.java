package com.malgn.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.crypto.Data;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(409)
                .body(new ErrorResponse("DATABASE_ERROR", "데이터 오류입니다."));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsernameException(DuplicateUsernameException e) {
        return ResponseEntity
                .status(409)
                .body(new ErrorResponse("USERNAME_DUPLICATE", e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(new ErrorResponse("USERNAME_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(401)
                .body(new ErrorResponse("BAD_CREDENTIALS", e.getMessage()));
    }

    // valid 에서 예외 발생할 때
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(400)
                .body(new ErrorResponse("VALIDATION_ERROR", e.getMessage()));
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContentNotFoundException(ContentNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(new ErrorResponse("CONTENT_NOT_FOUND", e.getMessage()));
    }
}
