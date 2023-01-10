package com.goodmortician.notes.repository;

import com.goodmortician.notes.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName (String name);
}
