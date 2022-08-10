package com.itl.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.itl.exceptions.MyException;
import com.itl.exceptions.NGException;

import reactor.core.publisher.Mono;

@Component
@Configuration
@PropertySource("classpath:master.properties")
@ConfigurationProperties(prefix = "urlcombo")
public class MasterFilter implements Filter {
	
	private RestTemplate restTemplate = new RestTemplate();
	
//	@Value("${spring.authservice.tokenvalidate}")
//	private String tokenvalidate;
	
//	@Value("${spring.application.name}")
//	private String selfservice;
	
	@Autowired
	private MasterAuth masterAuth;
	
	private Map<String, String> propmap = new HashMap<String, String>();
	
	public Map<String, String> getPropmap() {
		return propmap;
	}
	public void setPropmap(Map<String, String> propmap) {
		this.propmap = propmap;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		 String token =((HttpServletRequest) request).getHeader("AUTHORIZATION"); 
		 String appName =((HttpServletRequest) request).getHeader("appName"); 
	
         if (token!=null&&token.startsWith("API")) {
        	 var newToken = token.replace("API ", "");
        	 String  value=  masterAuth.getAuthCall(appName,newToken);
        	if(value.equals("true")) {
        		 chain.doFilter(request, response);
        	 }
        	 else {
        		 try {
        		 HttpServletResponse res = (HttpServletResponse)response;
        			 response.setContentType("text/html");
        			 response.getWriter().write("Service is UnAuthorized to Access");
        		 res.setStatus(401);
        	        return;
        	        
        		 }catch (Exception e) {
        			 e.printStackTrace();
				}
        		
        		// throw new NGException("service is not auth")
        	 }
         }
         else {
        	 chain.doFilter(request, response);
         }
	}
	
	//----------------------------------------------------
	 private Mono<Void> onError(ServletResponse response, String err, HttpStatus httpStatus) {
	       // ServerHttpResponse response = exchange.getResponse();
	        ((ServerHttpResponse) response).setStatusCode(httpStatus);
	        return ((ReactiveHttpOutputMessage) response).setComplete();
	    }

	
	
}
