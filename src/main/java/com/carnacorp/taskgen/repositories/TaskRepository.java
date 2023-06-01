package com.carnacorp.taskgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnacorp.taskgen.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
