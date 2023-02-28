package com.abe.Backend.restcontroller;

import com.abe.Backend.dto.CsrfResponseDTO;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.security.serviceImpl.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface UserRestController {
    @PostMapping("/sign-up")
    ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO);
    @PostMapping("/sign-in")
    ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO, BindingResult bindingResult,
                                             HttpServletRequest request, HttpServletResponse response);
    @PostMapping("/sign-out")
    ResponseEntity<String> signOut(HttpServletRequest request) throws ServletException;
    @GetMapping("/csrf")
    ResponseEntity<CsrfResponseDTO> csrf(HttpServletRequest request);
    @PostMapping("/admin")
    ResponseEntity<String> admin();
    @GetMapping("/current-user")
    ResponseEntity<SignInResponseDTO> currentUser(UserDetailsImpl userDetails);

}
