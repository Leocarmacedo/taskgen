package com.carnacorp.taskgen.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
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

import com.carnacorp.taskgen.entities.Response;
import com.carnacorp.taskgen.services.exceptions.OpenAiApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class GptService {

	private static final String OPENAI_API_KEY = "";
	private static final String OPENAI_MODEL_WHISPER = "whisper-1";
	private static final String OPENAI_MODEL_CHAT = "gpt-3.5-turbo";

	@Autowired
	private TrelloService trelloService;

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
			throw new OpenAiApiException("OpenAiApi request error.");
		}

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.openai.com/v1/chat/completions",
					HttpMethod.POST, requestEntity, String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				return responseEntity.getBody();
			} else {
				throw new OpenAiApiException("OpenAiApi request error.");
			}
		} catch (HttpClientErrorException e) {
			throw new OpenAiApiException("OpenAiApi request error.");
		}
	}

	public String getSystemResponse(String systemMessage, String userMessage) {

		String systemResponse = null;
		JSONObject jsonResponse = new JSONObject(this.callOpenAPI(systemMessage, userMessage));
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");
			System.out.println(jsonResponse);
			systemResponse = message.getString("content");
		}
		return systemResponse;

	}

	public Response getSystemReturn(String requestEntity) {

		Response response = new Response();
		JSONObject jsonResponse = new JSONObject(requestEntity);
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");

			if (firstChoice.getString("finish_reason").equals("function_call")) {
				JSONObject functionCall = message.getJSONObject("function_call");
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode jsonNode = objectMapper.readTree(functionCall.getString("arguments"));
					String name = jsonNode.get("name").asText();
					System.out.println(name);
					response.setListObj(taskService.findByName(name));
					return response;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			} else {
				response.setStringValue(message.getString("content"));
			}
		}

		return response;

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
			trelloService.createTrelloCard(result, depId);
			return result;
		} else {
			throw new OpenAiApiException("OpenAiApi request error.");
		}

	}

	public String callFunctions(String systemMessage, String userMessage) {

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

		Map<String, Object> funcObj = new HashMap<>();
		funcObj.put("name", "find_tasks_by_name");
		funcObj.put("description", "Econtra tarefas baseado em uma palavra chave como nome.");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("type", "object");

		Map<String, Object> properties = new HashMap<>();
		properties.put("name", Map.of("type", "string", "description", " O nome de uma tarefa"));
		properties.put("description", Map.of("type", "string", "description", " A descrição de uma tarefa"));

		parameters.put("properties", properties);
		parameters.put("required", new String[] { "name" });

		funcObj.put("parameters", parameters);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", OPENAI_MODEL_CHAT);
		requestBody.put("messages", new Object[] { systemObj, userObj });
		requestBody.put("functions", new Object[] { funcObj });
		requestBody.put("function_call", "auto");

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBodyJson;
		try {
			requestBodyJson = objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException e) {
			throw new OpenAiApiException("OpenAiApi request error.");
		}

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.openai.com/v1/chat/completions",
					HttpMethod.POST, requestEntity, String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				System.out.println(responseEntity.getBody());
				return responseEntity.getBody();
			} else {
				throw new OpenAiApiException("OpenAiApi request error.");
			}
		} catch (HttpClientErrorException e) {
			throw new OpenAiApiException("OpenAiApi request error.");
		}
	}

}
