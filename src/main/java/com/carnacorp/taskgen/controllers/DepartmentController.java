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

import com.carnacorp.taskgen.dto.BranchIdDTO;
import com.carnacorp.taskgen.dto.DepartmentDTO;
import com.carnacorp.taskgen.services.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

	@Autowired
	private DepartmentService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<DepartmentDTO> findById(@PathVariable Long id) {
		DepartmentDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> findAllByBranchId(@RequestBody BranchIdDTO branchId) {
		List<DepartmentDTO> dto = service.findAllByBranchId(branchId.getId());
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<DepartmentDTO> insert(@Valid @RequestBody DepartmentDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
