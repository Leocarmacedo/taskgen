package com.carnacorp.taskgen.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carnacorp.taskgen.dto.TaskDTO;
import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.entities.Task;
import com.carnacorp.taskgen.repositories.DepartmentRepository;
import com.carnacorp.taskgen.repositories.TaskRepository;
import com.carnacorp.taskgen.services.exceptions.DatabaseException;
import com.carnacorp.taskgen.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Transactional(readOnly = true)
	public List<TaskDTO> findByName(String name) {

		List<Task> result = repository.findByName(name);
		return result.stream().map(x -> new TaskDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public TaskDTO findById(Long id) {

		Task task = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new TaskDTO(task);
	}

	@Transactional(readOnly = true)
	public List<TaskDTO> findAll() {
		List<Task> result = repository.findAll();
		return result.stream().map(x -> new TaskDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public TaskDTO insert(TaskDTO dto) {
		Task entity = new Task();
		this.copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new TaskDTO(entity);
	}

	@Transactional
	public @Valid TaskDTO update(Long id, @Valid TaskDTO dto) {
		try {
			Task entity = repository.getReferenceById(id);
			this.copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new TaskDTO(entity);
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

	private void copyDtoToEntity(TaskDTO dto, Task entity) {
		
		Instant moment = Instant.now();

		Department department = departmentRepository.findById(dto.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		entity.setName(dto.getName());
		entity.setAuth(dto.isAuth());
		entity.setNeedAuth(dto.isNeedAuth());
		entity.setDeadLine(dto.getDeadLine());
		entity.setDescription(dto.getDescription());
		entity.setMoment(moment);
		entity.setDepartment(department);

	}

}
