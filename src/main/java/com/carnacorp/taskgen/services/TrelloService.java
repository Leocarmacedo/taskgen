package com.carnacorp.taskgen.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.carnacorp.taskgen.dto.GptDTO;
import com.carnacorp.taskgen.dto.TaskDTO;
import com.carnacorp.taskgen.services.exceptions.TrelloApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TrelloService {

	private static final String TRELLO_API_URL_TEMPLATE = "https://api.trello.com/1/cards?idList=%s&key=%s&token=%s";
	private static final String TRELLO_ID_LIST = "6467b5a996ce87891671c3ea";
	private static final String TRELLO_API_KEY = "e8573632ce0d5b3d18ceda38bd6c6cd5";
	private static final String TRELLO_API_TOKEN = "ATTA1b28f5c12eed7c8c23806256a95c545526fe85201fd9775cc70eb46ba5463eb106876B33";
	private static final String TRELLO_COOKIE = "dsc=39cded7d767dc8ddf9aa9e8dd1e47f321d7ec4996c482bbc4c3ae4d4d3f405a7; preAuthProps=s%3A6467b5492de24f974523a7cf%3AisEnterpriseAdmin%3Dfalse.NDgDv0kG7%2FtJoBNPCuolple9%2BgLG1n3bAKipDFBwiCY";

	@Autowired
	private TaskService taskService;

	public String createTrelloCard(String systemResponse, Long departmentId) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Cookie", TRELLO_COOKIE);
		headers.set("Accept", "application/json");

		HttpEntity<String> requestEntity = new HttpEntity<>(systemResponse, headers);

		String trelloApiUrl = String.format(TRELLO_API_URL_TEMPLATE, TRELLO_ID_LIST, TRELLO_API_KEY, TRELLO_API_TOKEN);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(trelloApiUrl,
					HttpMethod.POST, requestEntity, String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				ObjectMapper objectMapper = new ObjectMapper();

				// Converter a string JSON em um objeto Java
				GptDTO dto = objectMapper.readValue(systemResponse, GptDTO.class);

				TaskDTO taskDto = new TaskDTO();
				String dateString = dto.getDue();
				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
				LocalDate localDate = LocalDate.parse(dateString, formatter);

				taskDto.setName(dto.getName());
				taskDto.setDescription(dto.getDesc());
				taskDto.setDeadLine(localDate);
				taskDto.setDepartmentId(departmentId);

				taskService.insert(taskDto);

				return responseEntity.getBody();
			} else {
				throw new TrelloApiException("Trello API request error.");
			}
		} catch (HttpClientErrorException | JsonProcessingException e) {
			throw new TrelloApiException("Trello API request error.");
		}
	}
}

