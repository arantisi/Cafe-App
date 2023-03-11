package com.abe.Backend.serviceimpl;

import com.abe.Backend.constant.RoleEnum;
import com.abe.Backend.dto.CsrfResponseDTO;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.entity.RoleEntity;
import com.abe.Backend.entity.UserEntity;
import com.abe.Backend.repository.RoleRepository;
import com.abe.Backend.repository.UserRepository;
import com.abe.Backend.security.serviceImpl.UserDetailsImpl;
import com.abe.Backend.service.UserService;
import com.abe.Backend.utility.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@DependsOn("securityFilterChain")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RememberMeServices rememberMeServices;
    @Override
    public ResponseEntity<String> signUp(SignUpRequestDTO signUpRequestDTO) {
        if (userRepository.existsByUserName(signUpRequestDTO.getUserName())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByUserEmail(signUpRequestDTO.getUserEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(signUpRequestDTO.getUserName());
        userEntity.setUserPassword(passwordEncoder.encode(signUpRequestDTO.getUserPassword()));
        userEntity.setUserEmail(signUpRequestDTO.getUserEmail());
        userEntity.setUserPhoneNumber(signUpRequestDTO.getUserPhoneNumber());

        Set<String> roleEntitySetJson = signUpRequestDTO.getRoleEntitySet();
        Set<RoleEntity> roleEntitySetJava = new HashSet<>();

        if (roleEntitySetJson == null) {
            RoleEntity roleEntityUser1 = roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleEntitySetJava.add(roleEntityUser1);
        } else {
            roleEntitySetJson.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        RoleEntity roleEntityAdmin = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntitySetJava.add(roleEntityAdmin);

                        break;
                    case "ROLE_MODERATOR":
                        RoleEntity roleEntityModerator = roleRepository.findByRoleName(RoleEnum.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntitySetJava.add(roleEntityModerator);

                        break;
                    default:
                        RoleEntity roleEntityUser2 = roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntitySetJava.add(roleEntityUser2);
                }
            });
        }

        userEntity.setRoleEntitySet(roleEntitySetJava);
        userRepository.save(userEntity);

        return ResponseEntity.ok("User registered successfully!");
    }

    @Override
    public ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO, BindingResult bindingResult,
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
        log.info("User {} logged in.", userDetails.getAuthorities());
        rememberMeServices.loginSuccess(request, response, auth);
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        Set<String> userRoleSet = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        signInResponseDTO.setUserId(userDetails.getUserId());
        signInResponseDTO.setUserName(userDetails.getUsername());
        signInResponseDTO.setUserEmail(userDetails.getUserEmail());
        signInResponseDTO.setUserPhoneNumber(userDetails.getUserPhoneNumber());
        signInResponseDTO.setUserStatus(userDetails.getUserStatus());
        signInResponseDTO.setUserRoleSet(userRoleSet);


        return ResponseEntity.ok().body(signInResponseDTO);
    }

    @Override
    public ResponseEntity<String> signOut(HttpServletRequest request) throws ServletException {
        request.logout();
        return ResponseEntity.ok().body("hiiiiiiiii");
    }

    @Override
    public ResponseEntity<SignInResponseDTO> currentUser(UserDetailsImpl userDetails) {
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        Set<String> userRoleSet = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        signInResponseDTO.setUserId(userDetails.getUserId());
        signInResponseDTO.setUserName(userDetails.getUsername());
        signInResponseDTO.setUserEmail(userDetails.getUserEmail());
        signInResponseDTO.setUserPhoneNumber(userDetails.getUserPhoneNumber());
        signInResponseDTO.setUserStatus(userDetails.getUserStatus());
        signInResponseDTO.setUserRoleSet(userRoleSet);
        return ResponseEntity.ok().body(signInResponseDTO);

    }

    @Override
    public ResponseEntity<CsrfResponseDTO> csrf(HttpServletRequest request) {
//        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
//        log.info(csrf.toString());
//        return ResponseEntity.ok().body(new CsrfResponseDTO(csrf.getToken()));
        return null;
    }
}
