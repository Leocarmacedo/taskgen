package com.carnacorp.taskgen.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.carnacorp.taskgen.entities.Task;

public class TaskDTO {

	private Long id;
	private String name;
	private String description;
	private Instant moment;
	private LocalDate deadLine;
	private boolean needAuth;
	private boolean auth;

	private Long departmentId;

	public TaskDTO() {
	}

	public TaskDTO(Long id, String name, String description, Instant moment, LocalDate deadLine, boolean needAuth,
			boolean auth) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.moment = moment;
		this.deadLine = deadLine;
		this.needAuth = needAuth;
		this.auth = auth;
	}

	public TaskDTO(Task entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		moment = entity.getMoment();
		deadLine = entity.getDeadLine();
		needAuth = entity.isNeedAuth();
		auth = entity.isAuth();
		departmentId = entity.getDepartment().getId();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public LocalDate getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
