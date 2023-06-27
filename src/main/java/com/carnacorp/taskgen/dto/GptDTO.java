package com.carnacorp.taskgen.dto;

public class GptDTO {

	private String name;
	private String desc;
	private String due;

	public GptDTO() {
	}

	public GptDTO(String name, String desc, String due) {
		this.name = name;
		this.desc = desc;
		this.due = due;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

}
