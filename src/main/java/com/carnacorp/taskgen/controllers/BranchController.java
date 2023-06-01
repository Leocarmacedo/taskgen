package com.carnacorp.taskgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
		BranchDTO dto =  service.findById(id);
		return ResponseEntity.ok(dto);
	}

}
