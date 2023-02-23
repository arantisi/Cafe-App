package com.abe.Backend.restcontrollerimpl;

import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.restcontroller.UserRestController;
import com.abe.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
public class UserRestControllerImpl implements UserRestController {
    private final UserService userService;
    @Override
    public ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO) {
        return ResponseEntity.ok().body("hi");
    }

    @Override
    public ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> signOut() {
        return null;
    }
}
