package com.carnacorp.taskgen.dto;

public class TrelloCardDTO {

	private Long id;
	private String content;
	private Long departmentId;

	public TrelloCardDTO() {
	}

	public TrelloCardDTO(Long id, String content, Long departmentId) {
		this.id = id;
		this.content = content;
		this.departmentId = departmentId;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

}
