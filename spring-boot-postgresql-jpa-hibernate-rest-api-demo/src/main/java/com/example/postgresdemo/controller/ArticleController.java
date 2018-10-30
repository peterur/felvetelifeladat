package com.example.postgresdemo.controller;


import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Article;
import com.example.postgresdemo.repository.ArticleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/api/article/list")
    public String getArticles() throws IOException {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	List<Article> list = articleRepository.findAll();
    	String json=mapper.writeValueAsString(list);
        JsonNode actualObj = mapper.readTree(json);
    	for (JsonNode articleNode : actualObj) {
    	    if (articleNode instanceof ObjectNode) {
    	        ObjectNode object = (ObjectNode) articleNode;
    	        object.remove("text");
    	     	    }
    	}
    	
    	
        return actualObj.toString();
    }


    @PostMapping("/api/article/save")
    public Article createArticle(@Valid @RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/api/article/{id}")
    public String getArticle(@PathVariable Long id) throws ResourceNotFoundException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	Article article= articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article is not found with id " + id) );   	
    	
    	String jsonInString = mapper.writeValueAsString(article) ;
    	
    	JsonNode actualObj = mapper.readTree(jsonInString);
    	ObjectNode object = (ObjectNode) actualObj;
        object.remove("id");
        
        jsonInString = mapper.writeValueAsString(actualObj) ;
    	
        String[] words = article.getText().split("\\s+");
	    
    	jsonInString=jsonInString.substring(0, jsonInString.length() - 1);
    	jsonInString+=",\"wordcount\":\""+words.length+"\"}";
    	
    	
    	
    	return jsonInString;
    	  }


}
