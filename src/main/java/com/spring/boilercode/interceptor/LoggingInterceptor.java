package com.spring.boilercode.interceptor;

import com.spring.boilercode.Monitor.RestClientMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {


    private final RestClientMonitor restClientMonitor;

    public LoggingInterceptor(RestClientMonitor restClientMonitor) {
        this.restClientMonitor = restClientMonitor;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        restClientMonitor.getTotalExternalRequestsCounter().increment();
        logRequest(request, body);
        try {
            ClientHttpResponse response = execution.execute(request, body);
            if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                restClientMonitor.getFailedExternalRequestsCounter().increment();
            }
            logResponse(response);
            return response;
        } catch (IOException e) {
            restClientMonitor.getFailedExternalRequestsCounter().increment();
            throw e;
        }
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Response body: {}", inputStringBuilder.toString());
    }
}
