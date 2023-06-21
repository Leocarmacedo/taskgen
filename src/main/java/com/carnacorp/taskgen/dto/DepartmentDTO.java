package com.carnacorp.taskgen.dto;

import java.util.ArrayList;
import java.util.List;

import com.carnacorp.taskgen.entities.Department;
import com.carnacorp.taskgen.entities.Task;
import com.carnacorp.taskgen.entities.User;

public class DepartmentDTO {

	private Long id;
	private String name;

	private List<UserDTO> users = new ArrayList<>();

	private List<TaskDTO> tasks = new ArrayList<>();

	public DepartmentDTO(Long id, String name, List<UserDTO> users, List<TaskDTO> tasks) {
		this.id = id;
		this.name = name;
		this.users = users;
		this.tasks = tasks;
	}

	public DepartmentDTO(Department entity) {
		id = entity.getId();
		name = entity.getName();

		for (User user : entity.getUsers()) {
			users.add(new UserDTO(user));
		}

		for (Task task : entity.getTasks()) {
			tasks.add(new TaskDTO(task));
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public List<TaskDTO> getTasks() {
		return tasks;
	}

}
