package com.abe.Backend.service;

import com.abe.Backend.dto.CsrfResponseDTO;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.security.serviceImpl.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserService {
    ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO);
    ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO, BindingResult bindingResult,
                                             HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<String> signOut(HttpServletRequest request) throws ServletException;
    ResponseEntity<SignInResponseDTO> currentUser(UserDetailsImpl userDetails);
    ResponseEntity<CsrfResponseDTO> csrf(HttpServletRequest request);
}
