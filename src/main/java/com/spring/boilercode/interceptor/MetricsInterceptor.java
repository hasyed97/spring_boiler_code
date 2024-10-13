package com.spring.boilercode.interceptor;

import com.spring.boilercode.Monitor.RestClientMonitor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MetricsInterceptor implements HandlerInterceptor {

    private final RestClientMonitor restClientMonitor;

    public MetricsInterceptor(RestClientMonitor restClientMonitor) {
        this.restClientMonitor = restClientMonitor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        restClientMonitor.getTotalIncomingRequestsCounter().increment();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (response.getStatus() >= 400) {
            restClientMonitor.getFailedIncomingRequestsCounter().increment();
        } else if (response.getStatus() == 200) {
            restClientMonitor.getSuccessfulIncomingRequestsCounter().increment();
        }
        // Example for invalid requests: check if some condition is met
        // if (/* invalid condition */) {
        //    invalidIncomingRequestsCounter.increment();
        // }
    }
}

