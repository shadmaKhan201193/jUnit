package com.itl.configuration;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;


@Configuration
public class CacheConfig {


	@Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
          .withCacheConfiguration("itemCache",
            RedisCacheConfiguration.defaultCacheConfig()
            	.entryTtl(Duration.ofMinutes(10)));
    }

	
	
}
