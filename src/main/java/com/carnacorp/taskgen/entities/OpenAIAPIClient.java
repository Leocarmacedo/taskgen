package com.carnacorp.taskgen.entities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAIAPIClient {
	private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
	private static final String OPENAI_API_KEY = "api-key";
	private static final String OPENAI_MODEL = "gpt-3.5-turbo";

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
        requestBody.put("model", OPENAI_MODEL);
        requestBody.put("messages", new Object[]{systemObj, userObj});

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
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

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
}
