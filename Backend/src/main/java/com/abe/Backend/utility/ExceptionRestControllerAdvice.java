package com.abe.Backend.utility;

import com.abe.Backend.constant.Constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionRestControllerAdvice {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> generalExceptionHandler(Exception exception){
//        log.error("unknown Exception", exception.getMessage());
//        return ResponseEntity.internalServerError().body(Constant.UNKNOWN_EXCEPTION);
//    }
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> SecurityExceptionHandler(SecurityException exception){
        log.error("Invalid JWT signature: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(Constant.SECURITY_EXCEPTION);
    }
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<String> MalformedJwtExceptionHandler(MalformedJwtException exception){
//        log.error("Invalid JWT token: {}", exception.getMessage());
//        return ResponseEntity.badRequest().body(Constant.MALFORMEDJWT_EXCEPTION);
//    }
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<String> ExpiredJwtExceptionHandler(ExpiredJwtException exception){
//        log.error("JWT token is expired: {}", exception.getMessage());
//        return ResponseEntity.badRequest().body(Constant.EXPIREDJWT_EXCEPTION);
//    }
//    @ExceptionHandler(UnsupportedJwtException.class)
//    public ResponseEntity<String> UnsupportedJwtExceptionHandler(UnsupportedJwtException exception){
//        log.error("JWT token is unsupported: {}", exception.getMessage());
//        return ResponseEntity.badRequest().body(Constant.UNSUPPORTEDJWT_EXCEPTION);
//    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentExceptionHandler(IllegalArgumentException exception){
        log.error("JWT claims string is empty: {}", exception.getMessage());
        return ResponseEntity.badRequest().body(Constant.ILLEGALARGUMENT_EXCEPTION);
    }
}
