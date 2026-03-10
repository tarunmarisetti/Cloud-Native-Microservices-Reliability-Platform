package com.apigateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import org.springframework.cloud.client.circuitbreaker.Customizer;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

//	custom route configuration and for custom headers for request or response
	@Bean
	public RouteLocator RouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/myapp/department/**")
						.filters(f -> f.rewritePath("/myapp/department/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								//adding retry to the department service for only get methods (idempotent method)
								.retry(retryConfig -> retryConfig.setRetries(3)
												.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)
								))
						.uri("lb://DEPARTMENT"))
				.route(p -> p
						.path("/myapp/organization/**")
						.filters(f -> f.rewritePath("/myapp/organization/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                               //adding rate limiter to the organization service
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
										.setKeyResolver(userKeyResolver())))
						.uri("lb://ORGANIZATION"))
				.route(p -> p
						.path("/myapp/employee/**")
						.filters(f -> f.rewritePath("/myapp/employee/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								//adding circuit breaker for employee service
								.circuitBreaker(config -> config.setName("employeeCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://EMPLOYEE")).build();

	}
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}
