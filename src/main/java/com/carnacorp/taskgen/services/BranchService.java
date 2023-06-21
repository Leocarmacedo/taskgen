package com.carnacorp.taskgen.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<BranchDTO> findAll() {
		List<Branch> result = repository.findAll();
		return result.stream().map(x -> new BranchDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public BranchDTO insert(BranchDTO dto) {
		Branch entity = new Branch();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new BranchDTO(entity);
	}

}
