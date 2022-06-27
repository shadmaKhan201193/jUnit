package com.itl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.itl.configuration.interceptors.LogInterceptor;
//import com.netflix.appinfo.ApplicationInfoManager;
//import com.netflix.appinfo.InstanceInfo;

@SpringBootApplication
@ComponentScan(basePackages = { "com.itl" })
@EnableEurekaClient
@EnableCaching
@Configuration
public class MasterServiceApplication implements WebMvcConfigurer {
	
	@Value("${spring.application.name}")
	private String appName;
	
	//@Autowired
//	private ApplicationInfoManager applicationInfoManager;
	
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(MasterServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MasterServiceApplication.class, args);
	}

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MasterServiceApplication.class);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	System.out.println("setting LogInterceptor");
//    	logger.info("Getting the random port info>>" + randomPort);
//    	InstanceInfo applicationInfo = applicationInfoManager.getInfo();
//    	logger.info("Getting the Application IP Address info>>" + applicationInfo.getIPAddr());
//    	logger.info("Getting the Application Port info>>" + applicationInfo.getPort());
//    	logger.info("Getting the Application hostname info>>" + applicationInfo.getHostName());
//    	logger.info("Getting the Application App Name info>>" + applicationInfo.getAppName()); 
//    	logger.info("*********************");
//    	logger.info("Local Port>>>>>" + env.getProperty("local.server.port"));
    	/*logger.info("Getting the Environment IP Address info>>" + env.getProperty("spring.cloud.client.ip-address"));
    	logger.info("Getting the Environment hostname info>>" + env.getProperty("spring.cloud.client.hostname")); */
    	//registry.addInterceptor(new LogInterceptor("MasterService","172.21.1.78","SEZNB152"));
    	registry.addInterceptor(new LogInterceptor(appName));
//        registry.addInterceptor(new LogInterceptor(appName,applicationInfo.getIPAddr(), applicationInfo.getHostName()));
    }
    
	/*
	 * @Bean public Sampler defaultSampler() { return Sampler.ALWAYS_SAMPLE; }
	 * 
	 * @Bean public CurrentTraceContext currentTraceContext() { return
	 * CurrentTraceContext.Default.create(); }
	 */
    /*@Bean
    ScopeDecorator threadContextScopeDecorator() {
    	//builder.addScopeDecorator(new ThreadContextScopeDecorator());
    /*	return ThreadLocalCurrentTraceContext.newBuilder()
    			.addScopeDecorator(MDCScopeDecorator.create())
    			.build().; */
    	//return new ThreadContextScopeDecorator();
    	
    /*	return ThreadLocalCurrentTraceContext.newBuilder()
    			.addScopeDecorator(new ThreadContextScopeDecorator().create())
    			.build().;
    } */
    
    /*@Bean 
    public Tracing tracing(@Value("${bundle:application:spring.application.name}") String serviceName) {
	    return Tracing.newBuilder()
	    		.localServiceName(serviceName)
	    		.currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder()
	    		        .addScopeDecorator(ThreadContextScopeDecorator.get())
	    		        .build()
	    		    )
	        .build();
    } */ 
    
    @Bean
    @LoadBalanced
	public RestTemplate getRestTemplate() {
    	HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    	//clientHttpRequestFactory.setConnectTimeout(3000);
    	RestTemplate rt = new RestTemplate();
    	rt.setRequestFactory( clientHttpRequestFactory);
        return rt;
	}
//    @Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		System.out.println("setting LogInterceptor");
//		registry.addInterceptor(new LogInterceptor(appName));
//	}
}
