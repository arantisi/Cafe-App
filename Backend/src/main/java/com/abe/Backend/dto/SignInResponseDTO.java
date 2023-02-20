package com.abe.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInResponseDTO {
    private long userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userStatus;
    private Set<String> userRoleSet;
}
