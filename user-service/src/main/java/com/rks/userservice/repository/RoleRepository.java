package com.rks.userservice.repository;

import com.rks.userservice.entities.Role;
import com.rks.userservice.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
