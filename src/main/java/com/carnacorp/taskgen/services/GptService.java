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
				.header("Authorization", "API_KEY")
				.body("{\r\n  \"model\": \"gpt-3.5-turbo\",\r\n  \"temperature\": 0,\r\n  \"messages\": [\r\n        {\"role\": \"system\", \"content\": \"Você é um assistente de resumo de solicitações. Você receberá uma solicitação e precisará apenas resumir em poucas palavras e responder APENAS com um objeto json dessa forma: name: [Texto] desc: [Texto] due: [Data no formato yyyy-MM-dd]. Lembrando que due é a data de vencimento e caso não tenha sido informada, preencher com 'nao informado'. Considere que hoje é dia " + hoje + ". É importante ressaltar que sua resposta irá conter somente o objeto Json.\"},\r\n"
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

}
