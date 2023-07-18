package com.carnacorp.taskgen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carnacorp.taskgen.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM TB_TASK WHERE UPPER(name) LIKE CONCAT('%', UPPER(:name), '%')")
	List<Task> findByName(String name);

}
