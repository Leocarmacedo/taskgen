package com.carnacorp.taskgen.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carnacorp.taskgen.dto.TrelloCardDTO;
import com.carnacorp.taskgen.services.GptService;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping(value = "/gpt")
public class GptController {

	@Autowired
	private GptService service;

	@PostMapping("/trello")
	public String insertTrelloCard(@RequestBody TrelloCardDTO trelloDto) throws UnirestException {
		LocalDate hoje = LocalDate.now();
		String systemMessage = "Você é um assistente de resumo de solicitações. Você receberá uma solicitação e precisará responder APENAS com um objeto json dessa forma: name: [Texto] desc: [Texto da solicitação reescrita com correções gramaticais e de concordância.] due: [Data no formato yyyy-MM-dd]. Lembrando que due é a data de vencimento e caso não tenha sido informada, criar o objeto sem a data. Considere que hoje é dia "
				+ hoje + ". É importante ressaltar que sua resposta irá conter somente o objeto Json.";

		String systemResponse = service.getSystemResponse(systemMessage, trelloDto.getContent());

		service.trelloCard(systemResponse, trelloDto.getDepartmentId());

		return systemResponse;
	}

	@GetMapping("/")
	public String index(Model model) {
		String valorDoBackend = "Hello, World!";
		model.addAttribute("valorDoBackend", valorDoBackend);
		return "index";
	}

}
