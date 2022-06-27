package com.itl.configuration.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogInterceptor implements HandlerInterceptor {

	private String appName;
	
	public LogInterceptor(String appName) {
		super();
		this.appName = appName;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) {
		//MDC.clear();
		MDC.put("requestAppName", appName);
		return true;
	}
	

}