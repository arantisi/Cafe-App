package com.abe.Backend.repository;

import com.abe.Backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserEmail(String userEmail);
    Optional<UserEntity> findByUserPhoneNumber(String userPhoneNumber);
    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserPhoneNumber(String userEmail);

}
