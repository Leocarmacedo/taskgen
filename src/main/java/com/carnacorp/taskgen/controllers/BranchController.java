package com.carnacorp.taskgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carnacorp.taskgen.dto.BranchDTO;
import com.carnacorp.taskgen.services.BranchService;

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
	public ResponseEntity<Page<BranchDTO>> findAll(Pageable pageable) {
		Page<BranchDTO> dto = service.findAll(pageable);
		return ResponseEntity.ok(dto);
	}

}
