package com.carnacorp.taskgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
