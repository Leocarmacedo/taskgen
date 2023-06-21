package com.carnacorp.taskgen.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.dto.DepartmentDTO;
import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.repositories.BranchRepository;
import com.carnacorp.taskgen.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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

	@Transactional
	public @Valid BranchDTO update(Long id, @Valid BranchDTO dto) {
		try {
			Branch entity = repository.getReferenceById(id);
			entity.setName(dto.getName());

			entity.getDepartments().clear();
			for (DepartmentDTO depDto : dto.getDepartments()) {
				Department dep = new Department();
				dep.setId(depDto.getId());
				entity.getDepartments().add(dep);
			}

			entity = repository.save(entity);
			return new BranchDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

}
