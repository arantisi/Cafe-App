package com.abe.Backend.restcontroller;

import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/user")
public interface UserRestController {
    @PostMapping("/sign-up")
    ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO);
    @PostMapping("/sign-in")
    ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO);
    @PostMapping("/sign-out")
    ResponseEntity<String> signOut();
}
