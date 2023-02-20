package com.abe.Backend.service;

import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO);
    ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO);
    ResponseEntity<String> signOut();
}
