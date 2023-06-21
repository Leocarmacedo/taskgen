package com.carnacorp.taskgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.repositories.BranchRepository;
import com.carnacorp.taskgen.services.exceptions.ResourceNotFoundException;

@Service
public class BranchService {

	@Autowired
	private BranchRepository repository;

	@Transactional(readOnly = true)
	public BranchDTO findById(Long id) {

		Branch branch = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new BranchDTO(branch);
	}

	@Transactional(readOnly = true)
	public Page<BranchDTO> findAll(Pageable pageble) {
		Page<Branch> result = repository.findAll(pageble);
		return result.map(x -> new BranchDTO(x));
	}

}
