package com.carnacorp.taskgen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	List<Department> findByBranchId(Long branchId);

}
