package com.tool.vincicasa;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

	private ObjectMapper objectMapper;

	private List<String> excludedFields;

	public LoggingInterceptor(ObjectMapper objectMapper, List<String> excludedFields) {
		super();
		this.objectMapper = objectMapper;
		this.excludedFields = excludedFields;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	private void logRequest(HttpRequest request, byte[] body) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("URI: {}", request.getURI());
			LOGGER.debug("HTTP Method: {}", request.getMethod());
			LOGGER.debug("HTTP Headers: {}", headersToString(request.getHeaders()));

			if (body != null && body.length > 0) {
				try {
					Map<String, Object> requestBody = jsonToMap(new String(body, StandardCharsets.UTF_8));
					if (!requestBody.isEmpty()) {
						LOGGER.debug("Request Body: {}",
								objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody));
					}
				} catch (IOException e) {
					LOGGER.warn("Errore log request");
					LOGGER.debug("Request Body: {}", new String(body, StandardCharsets.UTF_8));
				}
			}
		}
	}

	private void logResponse(ClientHttpResponse response) {
		if (LOGGER.isDebugEnabled()) {
			try {
				LOGGER.debug("HTTP Status Code: {}", response.getRawStatusCode());
				LOGGER.debug("Status Text: {}", response.getStatusText());
				LOGGER.debug("HTTP Headers: {}", headersToString(response.getHeaders()));

				String payload = new String(response.getBody().readAllBytes());
				Map<String, Object> requestBody = jsonToMap(payload);

				if (!requestBody.isEmpty()) {
					LOGGER.debug("Response Body: {}",
							objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody));
				}

			} catch (IOException e) {
				LOGGER.error("Errrore debug response", e);
			}
		}
	}

	private Map<String, Object> jsonToMap(String payload) throws IOException {
		Map<String, Object> result = new HashMap<>();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		for (Map.Entry<String, Object> entry : objectMapper.readValue(payload, typeRef).entrySet()) {
			if (entry.getValue() == null || (entry.getValue() != null && !excludedFields.contains(entry.getKey()))) {
				result.put(entry.getKey(), entry.getValue());
			} else {
				LOGGER.debug("'{}': contenuto omesso perchè appartiene ai campi da non loggare", entry.getKey());
				result.put(entry.getKey(), "---------");
			}
		}

		return result;
	}

	private String headersToString(HttpHeaders headers) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, List<String>> entry : headers.entrySet()) {
			builder.append(entry.getKey()).append("=[");
			for (String value : entry.getValue()) {
				builder.append(value).append(",");
			}
			builder.setLength(builder.length() - 1); // Get rid of trailing comma
			builder.append("],");
		}
		builder.setLength(builder.length() - 1); // Get rid of trailing comma
		return builder.toString();
	}
}