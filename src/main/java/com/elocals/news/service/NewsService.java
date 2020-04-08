package com.elocals.news.service;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.elocals.news.dto.SourceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NewsService {
	
	@Autowired
	Environment env;
	
	
	
	public String getSources(String category) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("sources"));
		builder.queryParam("apikey", "4190b257a03e4582bcb907a2c248ab6a");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate=new RestTemplate();
	
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity(headers), String.class);
		
		
		String srcname=null;
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			
			 
			SourceDTO sourceDTO = objectMapper.readValue(response.getBody(), new TypeReference<SourceDTO>(){});
			
			List<String> filteredArticleList= sourceDTO.getSources().stream().filter(source -> source.getCategory().equalsIgnoreCase(category))
					.map(s->s.getName()).collect(Collectors.toList());
			
			 java.util.Map<String, Long> nameCount = filteredArticleList.stream().collect(Collectors.groupingBy(string -> string, Collectors.counting()));
		        nameCount.forEach((name, count) -> {
		        });
		        
		        srcname = Collections.max(nameCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            
           System.out.println(nameCount);
			
		} catch (JsonMappingException e) {
			System.out.println("Error"+e);
			
		} catch (JsonProcessingException e) {
			
			System.out.println("Error"+e);
		}
		return srcname;
	}
	
	
	
	
	
	
	

}
