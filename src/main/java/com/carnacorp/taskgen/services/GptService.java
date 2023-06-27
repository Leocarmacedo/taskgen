package com.carnacorp.taskgen.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carnacorp.taskgen.dto.GptDTO;
import com.carnacorp.taskgen.dto.TaskDTO;
import com.carnacorp.taskgen.entities.OpenAIAPIClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class GptService {

	@Autowired
	private TaskService taskService;

	public void trelloCard(String systemResponse, Long departmentId) throws UnirestException {

		Unirest.setTimeouts(0, 0);
		Unirest.post(
				"https://api.trello.com/1/cards?idList=6467b5a996ce87891671c3ea&key=e8573632ce0d5b3d18ceda38bd6c6cd5&token=ATTA1b28f5c12eed7c8c23806256a95c545526fe85201fd9775cc70eb46ba5463eb106876B33")
				.header("Accept", "application/json").header("Content-Type", "application/json")
				.header("Cookie",
						"dsc=39cded7d767dc8ddf9aa9e8dd1e47f321d7ec4996c482bbc4c3ae4d4d3f405a7; preAuthProps=s%3A6467b5492de24f974523a7cf%3AisEnterpriseAdmin%3Dfalse.NDgDv0kG7%2FtJoBNPCuolple9%2BgLG1n3bAKipDFBwiCY")
				.body(systemResponse).asString();

		ObjectMapper objectMapper = new ObjectMapper();

		TaskDTO taskDto = new TaskDTO();
		try {
			// Converter a string JSON em um objeto Java
			GptDTO dto = objectMapper.readValue(systemResponse, GptDTO.class);

			String dateString = dto.getDue();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
			LocalDate localDate = LocalDate.parse(dateString, formatter);

			taskDto.setName(dto.getName());
			taskDto.setDescription(dto.getDesc());
			taskDto.setDeadLine(localDate);
			taskDto.setDepartmentId(departmentId);

			taskService.insert(taskDto);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getSystemResponse(String systemMessage, String userMessage) {

		OpenAIAPIClient openAIAPIClient = new OpenAIAPIClient();

		String systemResponse = null;
		JSONObject jsonResponse = new JSONObject(openAIAPIClient.callOpenAPI(systemMessage, userMessage));
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");
			systemResponse = message.getString("content");
		}
		return systemResponse;

	}

}
