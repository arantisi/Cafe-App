package com.abe.Backend.serviceimpl;

import com.abe.Backend.constant.RoleEnum;
import com.abe.Backend.dto.SignInRequestDTO;
import com.abe.Backend.dto.SignInResponseDTO;
import com.abe.Backend.dto.SignUpRequestDTO;
import com.abe.Backend.entity.RoleEntity;
import com.abe.Backend.entity.UserEntity;
import com.abe.Backend.repository.RoleRepository;
import com.abe.Backend.repository.UserRepository;
import com.abe.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
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
    public ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> signOut() {
        return null;
    }
}
