package com.carnacorp.taskgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping
	public ResponseEntity<String> test(@RequestBody String content) throws UnirestException {
		content = service.insert(content);
		return ResponseEntity.ok(content);
	}
	
	@PostMapping(value = "/trello")
	public ResponseEntity<String> test2(@RequestBody String content) throws UnirestException {
		content = service.insert2(content);
		return ResponseEntity.ok(content);
	}

}
