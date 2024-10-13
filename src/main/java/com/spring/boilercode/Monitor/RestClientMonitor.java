package com.spring.boilercode.Monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class RestClientMonitor {
    private final Counter totalIncomingRequestsCounter;
    private final Counter failedIncomingRequestsCounter;
    private final Counter invalidIncomingRequestsCounter;
    private final Counter successfulIncomingRequestsCounter;
    private final Counter totalExternalRequestsCounter;
    private final Counter failedExternalRequestsCounter;

    public RestClientMonitor(MeterRegistry meterRegistry) {
        this.totalIncomingRequestsCounter = Counter.builder("rest.incoming.request")
                .description("Indicates all incoming requests")
                .register(meterRegistry);

        this.failedIncomingRequestsCounter = Counter.builder("failed.incoming.request")
                .description("Indicates all failed incoming requests")
                .register(meterRegistry);

        this.invalidIncomingRequestsCounter = Counter.builder("invalid.incoming.request")
                .description("Indicates all invalid requests")
                .register(meterRegistry);

        this.successfulIncomingRequestsCounter = Counter.builder("successful.incoming.request")
                .description("Indicates all successful requests")
                .register(meterRegistry);

        this.totalExternalRequestsCounter = Counter.builder("external.requests")
                .description("Indicates all external requests")
                .register(meterRegistry);

        this.failedExternalRequestsCounter = Counter.builder("failed.external.requests")
                .description("Indicates all failed external requests")
                .register(meterRegistry);

    }

}
