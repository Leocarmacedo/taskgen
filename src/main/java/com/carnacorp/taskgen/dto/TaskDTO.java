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
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Instant getMoment() {
		return moment;
	}

	public LocalDate getDeadLine() {
		return deadLine;
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public boolean isAuth() {
		return auth;
	}

}
