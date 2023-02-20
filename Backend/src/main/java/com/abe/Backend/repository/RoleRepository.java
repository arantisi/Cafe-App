package com.abe.Backend.repository;

import com.abe.Backend.constant.RoleEnum;
import com.abe.Backend.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleName(RoleEnum roleEnum);

}
