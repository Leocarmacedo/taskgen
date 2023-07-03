package com.carnacorp.taskgen.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.carnacorp.taskgen.dto.GptDTO;
import com.carnacorp.taskgen.dto.TaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class GptService {

	private static final String OPENAI_API_KEY = "OpenAiApiKey";
	private static final String OPENAI_MODEL_WHISPER = "whisper-1";
	private static final String OPENAI_MODEL_CHAT = "gpt-3.5-turbo";

	@Autowired
	private TaskService taskService;

	public String callOpenAPI(String systemMessage, String userMessage) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

		Map<String, Object> systemObj = new HashMap<>();
		systemObj.put("role", "system");
		systemObj.put("content", systemMessage);

		Map<String, Object> userObj = new HashMap<>();
		userObj.put("role", "user");
		userObj.put("content", userMessage);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", OPENAI_MODEL_CHAT);
		requestBody.put("messages", new Object[] { systemObj, userObj });

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBodyJson;
		try {
			requestBodyJson = objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.openai.com/v1/chat/completions",
					HttpMethod.POST, requestEntity, String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				return responseEntity.getBody();
			} else {
				// Lida com erros de chamada à API
				return null;
			}
		} catch (HttpClientErrorException.BadRequest e) {
			e.printStackTrace();
			return null;
		}
	}

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

		String systemResponse = null;
		JSONObject jsonResponse = new JSONObject(this.callOpenAPI(systemMessage, userMessage));
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");
			systemResponse = message.getString("content");
		}
		return systemResponse;

	}

	public String transcribeAudio(Path tempFilePath, Long depId) throws UnirestException {

		LocalDate hoje = LocalDate.now();
		String systemMessage = "Você é um assistente de resumo de solicitações. Você receberá uma solicitação e precisará responder APENAS com um objeto json dessa forma: name: [Texto] desc: [Texto da solicitação reescrita com correções gramaticais e de concordância.] due: [Data no formato yyyy-MM-dd]. Lembrando que due é a data de vencimento e caso não tenha sido informada, criar o objeto sem a data. Considere que hoje é dia "
				+ hoje + ". É importante ressaltar que sua resposta irá conter somente o objeto Json.";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("model", OPENAI_MODEL_WHISPER);
		body.add("file", new FileSystemResource(tempFilePath.toFile()));
		body.add("response_format", "text");

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.openai.com/v1/audio/transcriptions",
				HttpMethod.POST, requestEntity, String.class);

		// Remover o arquivo temporário
		try {
			Files.deleteIfExists(tempFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			String result = this.getSystemResponse(systemMessage, responseEntity.getBody());
			this.trelloCard(result, depId);
			return result;
		} else {
			// Lida com erros de chamada à API
			return null;
		}

	}

}
