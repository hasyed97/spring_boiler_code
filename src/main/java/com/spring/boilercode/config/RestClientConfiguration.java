package com.spring.boilercode.config;

import com.spring.boilercode.interceptor.LoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class RestClientConfiguration {
    @Value(("${http.connect.timeout.sec:5}"))
    private Integer httpConnectionTimeoutSec;

    @Value(("${http.read.timeout.sec: 10}"))
    private Integer httpReadTimeoutSec;
    @Value(("${http.request.timeout.sec: 5}"))
    private Integer httpRequestTimeoutSec;
    private final LoggingInterceptor loggingInterceptor;

    public RestClientConfiguration(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(loggingInterceptor);
        return builder
                .requestFactory(this::clientHttpRequestFactory)
                .interceptors(interceptors)
                .build();
    }
    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(500); // Adjust according to your expected load
        connectionManager.setDefaultMaxPerRoute(200); // Adjust according to your expected load

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(configureRequestConfig())
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
    private RequestConfig configureRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(httpConnectionTimeoutSec))   // Connection timeout
                .setResponseTimeout(Timeout.ofSeconds(httpReadTimeoutSec)) // Read timeout
                .setConnectionRequestTimeout(Timeout.ofSeconds(httpRequestTimeoutSec)) // Connection request timeout
                .build();
    }
}
