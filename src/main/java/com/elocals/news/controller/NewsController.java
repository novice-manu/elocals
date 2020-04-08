package com.elocals.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elocals.news.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsService service;
	
	
	@RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
	public String healthCheck() {
		
		
		return "Success";
	}
	
	
	@RequestMapping(value = "/sources", method = RequestMethod.GET)
	public ResponseEntity getSources(@RequestParam("category") String category) {
		ResponseEntity<String> response=null;
		String name=service.getSources(category);
		if (!name.isEmpty()) {
			
			response=new ResponseEntity<String>(name,HttpStatus.OK);
			
			return response;
			
		}else {
			response=new ResponseEntity<String>("NO FOUND",HttpStatus.BAD_REQUEST);
			return response;
		}
		
		
	}
	

}
