package com.carnacorp.taskgen.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.carnacorp.taskgen.entities.User;

public class UserCredentialsDTO {

	private Long id;
	private String name;
	private String email;
	private String departmentName;

	private List<String> roles = new ArrayList<>();

	public UserCredentialsDTO() {
	}

	public UserCredentialsDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		departmentName = entity.getDepartment().getName();
		for (GrantedAuthority role : entity.getRoles()) {
			roles.add(role.getAuthority());
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public List<String> getRoles() {
		return roles;
	}

}
