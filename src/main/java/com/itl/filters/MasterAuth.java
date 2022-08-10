package com.itl.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.itl.configuration.CacheBuilder;

@Component
public class MasterAuth {
	private static final List tokenList = null;

	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${spring.application.name}")
	private String selfservice;
	
	@Value("${spring.authservice.tokenvalidate}")
	private String tokenvalidate;
	
	@Autowired
	private CacheBuilder cacheBuilder;
	
	

	//@Cacheable(value = "tokenList",key="{#String.appName, #String.selfservice}", unless="#result == null")
	@Cacheable(value = "tokenList", key = "#token", unless="#result == null")
		public String getAuthCall(String appName,String token) {
	  	HttpHeaders headers = new HttpHeaders();
		headers.set("token", token);
		headers.set("appName", appName);
		headers.set("selfservice", selfservice);
	  	HttpEntity<String> entity = new HttpEntity<>("some body", headers);
	  	ResponseEntity<String>  value = restTemplate.exchange(tokenvalidate, HttpMethod.POST,entity, String.class) ;
	  	
	  	String dd=value.getBody();
	  	 Boolean newValue=Boolean.valueOf(dd); 
		return dd;
	}
	
	
	
	
}
