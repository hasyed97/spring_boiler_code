package com.spring.boilercode.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class RequestHelper {
    private final RestTemplate restTemplate;

    public RequestHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T callExternalGetApi(String baseUrl, Map<String, String> queryParams, Class<T> responseType) {
        // Build the URI with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        queryParams.forEach(uriBuilder::queryParam);

        String url = uriBuilder.toUriString();

        // Make the API call
        ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);

        // Handle the response (you can add more sophisticated error handling here)
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to call API: " + response.getStatusCode());
        }
    }

    public <T, R> T callExternalGetApi(String baseUrl, Map<String, String> queryParams, R requestBody, Class<T> responseType) {
        // Build the URI with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        queryParams.forEach(uriBuilder::queryParam);

        String url = uriBuilder.toUriString();

        // Set headers if needed
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set content type or other headers if necessary

        // Create an HttpEntity object with the request body and headers
        HttpEntity<R> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST API call
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

        // Handle the response (you can add more sophisticated error handling here)
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to call API: " + response.getStatusCode());
        }
    }
}
