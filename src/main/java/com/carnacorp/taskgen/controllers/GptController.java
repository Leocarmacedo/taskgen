package com.carnacorp.taskgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carnacorp.taskgen.services.GptService;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping(value = "/gpt")
public class GptController {

	@Autowired
	private GptService service;

	@PostMapping(value = "/trello")
	public ResponseEntity<String> createTask(@RequestBody String content) throws UnirestException {
		String result = service.createTask(content);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/")
	public String index(Model model) {
		String valorDoBackend = "Hello, World!";
		model.addAttribute("valorDoBackend", valorDoBackend);
		return "index";
	}

}
