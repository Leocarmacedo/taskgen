package com.carnacorp.taskgen.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.services.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {

	@Autowired
	private BranchService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<BranchDTO> findById(@PathVariable Long id) {
		BranchDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<List<BranchDTO>> findAll() {
		List<BranchDTO> dto = service.findAll();
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<BranchDTO> insert(@Valid @RequestBody BranchDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<BranchDTO> update(@PathVariable Long id, @Valid @RequestBody BranchDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}

}
