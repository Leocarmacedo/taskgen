package com.carnacorp.taskgen.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.dto.BranchMinDTO;
import com.carnacorp.taskgen.dto.DepartmentDTO;
import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.repositories.BranchRepository;
import com.carnacorp.taskgen.services.exceptions.DatabaseException;
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
	public List<BranchMinDTO> findAll() {
		List<Branch> result = repository.findAll();
		return result.stream().map(x -> new BranchMinDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public BranchDTO insert(BranchDTO dto) {
		Branch entity = new Branch();
		this.copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new BranchDTO(entity);
	}

	@Transactional
	public @Valid BranchDTO update(Long id, @Valid BranchDTO dto) {
		try {
			Branch entity = repository.getReferenceById(id);
			this.copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new BranchDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(BranchDTO dto, Branch entity) {
		entity.setName(dto.getName());

		entity.getDepartments().clear();
		for (DepartmentDTO depDto : dto.getDepartments()) {
			Department dep = new Department();
			dep.setId(depDto.getId());
			entity.getDepartments().add(dep);
		}
	}

}
