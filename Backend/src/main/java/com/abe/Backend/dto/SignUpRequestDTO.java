package com.abe.Backend.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;
    @Email
    @NotBlank
    private String userEmail;
    @NotBlank
    private String userPhoneNumber;
    private Set<String> roleEntitySet;
}
