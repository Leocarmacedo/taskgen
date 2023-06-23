package com.carnacorp.taskgen.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.carnacorp.taskgen.entities.User;

public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private String password;

	private Long departmentId;

	private List<String> roles = new ArrayList<>();

	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		password = entity.getPassword();
		departmentId = entity.getDepartment().getId();
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

	public String getPassword() {
		return password;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public List<String> getRoles() {
		return roles;
	}

}
