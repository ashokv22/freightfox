package com.freightfox.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.freightfox.demo.*"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Application {

	@Bean
	public CacheManager cacheManager() {
		// Configure your desired CacheManager implementation here (e.g., RedisCacheManager)
		return new ConcurrentMapCacheManager("pincode-distances");  // In-memory cache example
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
