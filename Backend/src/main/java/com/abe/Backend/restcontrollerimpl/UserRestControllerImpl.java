package com.abe.Backend.restcontrollerimpl;

import com.abe.Backend.dto.CsrfResponseDTO;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.restcontroller.UserRestController;
import com.abe.Backend.security.serviceImpl.UserDetailsImpl;
import com.abe.Backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
/////maybe interface add
//@DependsOn("securityFilterChain")
public class UserRestControllerImpl implements UserRestController {
    private final UserService userService;

    @Override
    public ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO) {
        return ResponseEntity.ok().body("hi");
    }

    @Override
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO signInRequestDTO, BindingResult bindingResult,
                                                    HttpServletRequest request, HttpServletResponse response) {
        return userService.signIn(signInRequestDTO,bindingResult,request,response);

    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> signOut(HttpServletRequest request) throws ServletException {
        return userService.signOut(request);
    }

    @Override
    public ResponseEntity<CsrfResponseDTO> csrf(HttpServletRequest request) {
        return userService.csrf(request);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok().body("adminnn");
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SignInResponseDTO> currentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.currentUser(userDetails);
    }


}
