package com.carnacorp.taskgen.entities;

import java.util.List;

import com.carnacorp.taskgen.dto.TaskDTO;

public class Response {

	private String stringValue;
	private List<TaskDTO> listObj;

	public Response() {
	}

	public Response(String stringValue, List<TaskDTO> listObj) {
		this.stringValue = stringValue;
		this.listObj = listObj;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public List<TaskDTO> getListObj() {
		return listObj;
	}

	public void setListObj(List<TaskDTO> listObj) {
		this.listObj = listObj;
	}

}
