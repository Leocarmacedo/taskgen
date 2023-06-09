package com.carnacorp.taskgen.dto;

import java.util.ArrayList;
import java.util.List;

import com.carnacorp.taskgen.entities.Branch;
import com.carnacorp.taskgen.entities.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BranchDTO {

	private Long id;

	@Size(min = 3, max = 30, message = "Nome precisa ter de 3 a 30 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;

	private List<DepartmentDTO> departments = new ArrayList<>();

	public BranchDTO() {
	}

	public BranchDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public BranchDTO(Branch entity) {
		id = entity.getId();
		name = entity.getName();
		for (Department dep : entity.getDepartments()) {
			departments.add(new DepartmentDTO(dep));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DepartmentDTO> getDepartments() {
		return departments;
	}

}
