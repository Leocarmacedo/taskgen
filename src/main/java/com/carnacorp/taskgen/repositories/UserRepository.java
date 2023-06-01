package com.carnacorp.taskgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
