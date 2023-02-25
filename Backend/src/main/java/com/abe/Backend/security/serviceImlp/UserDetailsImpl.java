package com.abe.Backend.security.serviceImlp;

import com.abe.Backend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetailsImpl implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long userId;
    private String userName;
//    @JsonIgnore
    private String userPassword;
    private String userEmail;

    private String userPhoneNumber;
    private String userStatus;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = userEntity.getRoleEntitySet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toSet());


        return new UserDetailsImpl(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getUserPassword(),
                userEntity.getUserEmail(),
                userEntity.getUserPhoneNumber(),
                userEntity.getUserStatus(),
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
