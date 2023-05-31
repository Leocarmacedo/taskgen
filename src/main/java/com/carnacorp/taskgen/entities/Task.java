package com.carnacorp.taskgen.entities;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant moment;
	private LocalDate deadLine;
	private boolean needAuth;
	private boolean auth;

	public Task() {
	}

	public Task(Long id, String name, String description, Instant moment, LocalDate deadLine, boolean needAuth,
			boolean auth) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.moment = moment;
		this.deadLine = deadLine;
		this.needAuth = needAuth;
		this.auth = auth;
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

}
