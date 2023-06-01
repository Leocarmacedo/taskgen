package com.carnacorp.taskgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
