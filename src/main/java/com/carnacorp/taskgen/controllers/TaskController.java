package com.carnacorp.taskgen.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carnacorp.taskgen.dto.TaskDTO;
import com.carnacorp.taskgen.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {
		TaskDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/name")
	public ResponseEntity<List<TaskDTO>> findByName(@RequestBody TaskDTO taskDto) {
		List<TaskDTO> dto = service.findByName(taskDto.getName());
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<List<TaskDTO>> findAll() {
		List<TaskDTO> dto = service.findAll();
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<TaskDTO> insert(@Valid @RequestBody TaskDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
