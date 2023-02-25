package com.abe.Backend.entity;

import com.abe.Backend.constant.Constant;
import com.abe.Backend.constant.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique=true)
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;
    @Column(unique=true)
    @Email
    @NotBlank
    private String userEmail;
    @Column(unique=true)
    @NotBlank
    private String userPhoneNumber;
    @NotBlank
    private String userStatus = Constant.DEFAULT_USER_STATUS;
    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
//    private Set<RoleEntity> roleEntitySet;
    private Set<RoleEntity> roleEntitySet = new HashSet<>(Arrays.asList(new RoleEntity(1, RoleEnum.ROLE_USER)));
}
