package com.abe.Backend.restcontrollerimpl;

import com.abe.Backend.dto.CsrfResponseDTO;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.entity.UserEntity;
import com.abe.Backend.restcontroller.UserRestController;
import com.abe.Backend.security.serviceImlp.UserDetailsImpl;
import com.abe.Backend.service.UserService;
import com.abe.Backend.utility.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Slf4j
/////maybe interface add
@DependsOn("securityFilterChain")
public class UserRestControllerImpl implements UserRestController {
    private final UserService userService;
    private final RememberMeServices rememberMeServices;




    @Override
    public ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO) {
        return ResponseEntity.ok().body("hi");
    }

    @Override
    public ResponseEntity<SignInResponseDTO> signIn(@Valid @RequestBody SignInRequestDTO signInRequestDTO, BindingResult bindingResult,
                                                    HttpServletRequest request, HttpServletResponse response) {
        if (request.getUserPrincipal() != null) {
            throw new AppException("Please logout first.");
        }
        if (bindingResult.hasErrors()) {
            throw new AppException("Invalid username or password");
        }
        try {
            request.login(signInRequestDTO.getUserName(), signInRequestDTO.getUserPassword());
        } catch (ServletException e) {
            throw new AppException("Invalid username or password");
        }
        Authentication auth = (Authentication) request.getUserPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        log.info("User {} logged in.", userDetails.getUsername());
        rememberMeServices.loginSuccess(request, response, auth);


        return ResponseEntity.ok().body(new SignInResponseDTO());

    }

    @Override
    public ResponseEntity<String> signOut() {
        return null;
    }

    @Override
    public ResponseEntity<CsrfResponseDTO> csrf(HttpServletRequest request) {
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        return ResponseEntity.ok().body(new CsrfResponseDTO(csrf.getToken()));
    }
}
