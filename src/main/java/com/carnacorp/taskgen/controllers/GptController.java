package com.carnacorp.taskgen.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/transcribe")
	public String transcribeAudio(@RequestParam("file") MultipartFile audioFile, Long departmentId) throws UnirestException {
		if (audioFile.isEmpty()) {
			return "O arquivo de áudio não foi fornecido.";
		}

		// Salvar o arquivo temporariamente em disco
		Path tempFilePath;
		try {
			tempFilePath = Files.createTempFile("audio", ".mp3");
			Files.copy(audioFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
			return service.transcribeAudio(tempFilePath, departmentId);
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro ao processar o arquivo de áudio.";
		}
		
    }
	

	@GetMapping("/")
	public String index(Model model) {
		String valorDoBackend = "Hello, World!";
		model.addAttribute("valorDoBackend", valorDoBackend);
		return "index";
	}

}
