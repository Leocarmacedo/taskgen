package com.carnacorp.taskgen.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.repositories.BranchRepository;

@Service
public class BranchService {
	
	@Autowired
	private BranchRepository repository;
	
	@Transactional
	public BranchDTO findById (Long id) {
		
		Optional<Branch> branch = repository.findById(id);
		return new BranchDTO(branch.get().getId(), branch.get().getName());
	}

}
