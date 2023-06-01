package com.carnacorp.taskgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{

}
