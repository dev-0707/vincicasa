package com.tool.vincicasa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.workshop.winh.service.rest.api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tool.vincicasa.estrazioni.ConcorsoMapper;
import com.tool.vincicasa.estrazioni.ConcorsoRepository;
import com.tool.vincicasa.estrazioni.ConcorsoService;
import com.workshop.winh.service.rest.api.client.ConcorsoApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
public class WinApplication implements CommandLineRunner {

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${rest.client.debugEnabled}")
	private Boolean debugEnabled;

	private static final Logger LOGGER = LoggerFactory.getLogger(WinApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WinApplication.class, args);
	}

	@Bean
	public ConcorsoService ConcorsoService(ConcorsoApi estrazioniAPI, ConcorsoRepository concorsoRepository,
			ConcorsoMapper concorsoMapper) {
		return new ConcorsoService(estrazioniAPI, concorsoRepository, concorsoMapper);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(new Date(1638471600000L));

	}

	@Bean
	public RestTemplate etbRestClient() {
		RestTemplate restTemplate = new RestTemplateBuilder()
				// .rootUri("https://www.vincicasa.it/sisal-gn-proxy-servlet-web/proxy/gntn-info-web/rest/gioco/vincicasa")
				.build();

		if (debugEnabled) {
			LOGGER.info("ETB Rest Client Debug {}", true);
			restTemplate.setInterceptors(addLoggingInterceptor());
			ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(
					new SimpleClientHttpRequestFactory());
			restTemplate.setRequestFactory(requestFactory);
		}

		return restTemplate;

	}

	private List<ClientHttpRequestInterceptor> addLoggingInterceptor() {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new LoggingInterceptor(objectMapper, new ArrayList()));
		return interceptors;
	}



	@Bean
	public ApiClient apiClient() {
		ApiClient apiClient = new ApiClient(etbRestClient());
		apiClient.setBasePath("https://www.vincicasa.it/sisal-gn-proxy-servlet-web/proxy/gntn-info-web/rest/gioco/vincicasa");
		return apiClient;
	}

}
