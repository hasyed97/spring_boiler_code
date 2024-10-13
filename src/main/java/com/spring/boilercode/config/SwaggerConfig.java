package com.spring.boilercode.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import org.springframework.web.method.HandlerMethod;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Proxy Connector")
                        .version("0.0.1")
                        .description("Spring - Boiler code for building production ready microservices")
                        .termsOfService("https://linkedin.com/in/hasan-syed-april97")
                );
    }
    @Bean
    public OperationCustomizer addCorrelationIdHeader() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter correlationIdHeader = new Parameter()
                    .in("header")
                    .schema(new StringSchema())
                    .name("x-correlation-id")
                    .description("Correlation ID for tracking the request")
                    .required(false);
            operation.addParametersItem(correlationIdHeader);
            return operation;
        };
    }
    @EventListener(ApplicationReadyEvent.class)
    public void printSwaggerUrl() {
        try {
            String serverHost = InetAddress.getLocalHost().getHostAddress();
            String swaggerUrl = String.format("Swagger UI: http://%s:%s/swagger-ui/index.html", serverHost, serverPort);
            log.info(swaggerUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
