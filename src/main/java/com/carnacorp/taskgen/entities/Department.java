package com.carnacorp.taskgen.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@OneToMany(mappedBy = "department")
	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "department")
	private Set<Task> tasks = new HashSet<>();

	public Department() {
	}

	public Department(Long id, String name, Branch branch) {
		this.id = id;
		this.name = name;
		this.branch = branch;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Set<User> getUsers() {
		return users;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

}
