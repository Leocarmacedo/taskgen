package com.carnacorp.taskgen.services;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class GptService {

	public String insert(String content) throws UnirestException {

		LocalDate hoje = LocalDate.now();

		Unirest.setTimeouts(0, 0);
		HttpResponse<JsonNode> response = Unirest.post("https://api.openai.com/v1/chat/completions")
				.header("Content-Type", "application/json")
				.header("Authorization", "api_key")
				.body("{\r\n  \"model\": \"gpt-3.5-turbo\",\r\n  \"temperature\": 0,\r\n  \"messages\": [\r\n        {\"role\": \"system\", \"content\": \"Você é um assistente de resumo de solicitações. Você receberá uma solicitação e precisará apenas resumir em poucas palavras e responder APENAS com um objeto json dessa forma: name: [Texto] desc: [Texto] due: [Data no formato yyyy-MM-dd]. Lembrando que due é a data de vencimento e caso não tenha sido informada, preencher com 'nao informado'. Considere que hoje é dia "
						+ hoje + ". É importante ressaltar que sua resposta irá conter somente o objeto Json.\"},\r\n"
						+ "{\"role\": \"user\", \"content\": \"" + content + "\"}\r\n    ]\r\n}\r\n")
				.asJson();

		String systemResponse = null;
		JSONObject jsonResponse = new JSONObject(response.getBody().toString());
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");
			systemResponse = message.getString("content");
		}

		return systemResponse;
	}

	public String insert2(String content) throws UnirestException {

		LocalDate hoje = LocalDate.now();

		Unirest.setTimeouts(0, 0);
		HttpResponse<JsonNode> response = Unirest.post("https://api.openai.com/v1/chat/completions")
				.header("Content-Type", "application/json")
				.header("Authorization", "api_key")
				.body("{\r\n  \"model\": \"gpt-3.5-turbo\",\r\n  \"temperature\": 0,\r\n  \"messages\": [\r\n        {\"role\": \"system\", \"content\": \"Você é um assistente de resumo de solicitações. Você receberá uma solicitação e precisará apenas resumir em poucas palavras e responder APENAS com um objeto json dessa forma: name: [Texto] desc: [Texto] due: [Data no formato yyyy-MM-dd]. Lembrando que due é a data de vencimento e caso não tenha sido informada, preencher com 'nao informado'. Considere que hoje é dia "
						+ hoje + ". É importante ressaltar que sua resposta irá conter somente o objeto Json.\"},\r\n"
						+ "{\"role\": \"user\", \"content\": \"" + content + "\"}\r\n    ]\r\n}\r\n")
				.asJson();

		String systemResponse = null;
		JSONObject jsonResponse = new JSONObject(response.getBody().toString());
		JSONArray choices = jsonResponse.getJSONArray("choices");
		if (choices.length() > 0) {
			JSONObject firstChoice = choices.getJSONObject(0);
			JSONObject message = firstChoice.getJSONObject("message");
			systemResponse = message.getString("content");
		}

		this.trelloCard(systemResponse);

		return systemResponse;
	}

	private String trelloCard(String systemResponse) throws UnirestException {

		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post(
				"https://api.trello.com/1/cards?idList=6467b5a996ce87891671c3e9&key=e8573632ce0d5b3d18ceda38bd6c6cd5&token=ATTA1b28f5c12eed7c8c23806256a95c545526fe85201fd9775cc70eb46ba5463eb106876B33")
				.header("key", "apikey")
				.header("token", "token")
				.header("Accept", "application/json").header("Content-Type", "application/json")
				.header("Cookie",
						"dsc=39cded7d767dc8ddf9aa9e8dd1e47f321d7ec4996c482bbc4c3ae4d4d3f405a7; preAuthProps=s%3A6467b5492de24f974523a7cf%3AisEnterpriseAdmin%3Dfalse.NDgDv0kG7%2FtJoBNPCuolple9%2BgLG1n3bAKipDFBwiCY")
				.body(systemResponse)
				.asString();
		
		return response.getBody();

	}

}
