package com.carnacorp.taskgen.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.DepartmentDTO;
import com.carnacorp.taskgen.dto.TaskDTO;
import com.carnacorp.taskgen.dto.UserDTO;
import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.entities.Task;
import com.carnacorp.taskgen.entities.User;
import com.carnacorp.taskgen.repositories.BranchRepository;
import com.carnacorp.taskgen.repositories.DepartmentRepository;
import com.carnacorp.taskgen.services.exceptions.DatabaseException;
import com.carnacorp.taskgen.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	@Autowired
	private BranchRepository branchRepository;

	@Transactional(readOnly = true)
	public DepartmentDTO findById(Long id) {

		Department department = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		return new DepartmentDTO(department);
	}

	@Transactional(readOnly = true)
	public List<DepartmentDTO> findAllByBranchId(Long branchId) {
		List<Department> result = repository.findByBranchId(branchId);
		return result.stream().map(x -> new DepartmentDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public DepartmentDTO insert(DepartmentDTO dto) {
		Department entity = new Department();
		this.copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new DepartmentDTO(entity);
	}

	@Transactional
	public @Valid DepartmentDTO update(Long id, @Valid DepartmentDTO dto) {
		try {
			Department entity = repository.getReferenceById(id);
			this.copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new DepartmentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Department not found");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Department not found");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(DepartmentDTO dto, Department entity) {

		Branch branch = branchRepository.findById(dto.getBranchId())
				.orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
		entity.setName(dto.getName());
		entity.setBranch(branch);

		entity.getUsers().clear();
		for (UserDTO userDto : dto.getUsers()) {
			User user = new User();
			user.setId(userDto.getId());
			entity.getUsers().add(user);
		}

		entity.getTasks().clear();
		for (TaskDTO taskDto : dto.getTasks()) {
			Task task = new Task();
			task.setId(taskDto.getId());
			entity.getTasks().add(task);
		}
	}

}
