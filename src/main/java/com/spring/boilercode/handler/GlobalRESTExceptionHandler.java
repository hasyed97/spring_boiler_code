package com.spring.boilercode.handler;

import com.spring.boilercode.Monitor.RestClientMonitor;
import com.spring.boilercode.enums.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalRESTExceptionHandler {
    private final RestClientMonitor restClientMonitor;

    public GlobalRESTExceptionHandler(RestClientMonitor restClientMonitor) {
        this.restClientMonitor = restClientMonitor;
    }

    @ExceptionHandler({ConstraintViolationException.class,
            UnexpectedTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Violation onConstraintValidationException(ConstraintViolationException e) {
        restClientMonitor.getInvalidIncomingRequestsCounter().increment();
        ConstraintViolation violation = e.getConstraintViolations().stream().findFirst().orElse(null);

        if (violation != null) {
            String violationPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            // Map field errors to APIResponse
            APIResponse apiResponse = APIResponse.valueOf(message);


            // Create the error response using the APIResponse
            Violation.Header header = Violation.Header.builder()
                    .statusCode(apiResponse.getStatus())
                    .message(apiResponse.getMessage())
                    .build();

            return Violation.builder()
                    .header(header)
                    .build();
        }

        // Default response if no field errors are present
        return Violation.builder()
                .header(Violation.Header.builder()
                        .statusCode(APIResponse.BAD_REQUEST.getStatus())
                        .message("Constraint Violation Exception")
                        .build())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Violation onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        // Retrieve the first field error
        restClientMonitor.getInvalidIncomingRequestsCounter().increment();
        FieldError fieldError = e.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        if (fieldError != null) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            // Map field errors to APIResponse
            APIResponse apiResponse = APIResponse.valueOf(message);


            // Create the error response using the APIResponse
            Violation.Header header = Violation.Header.builder()
                    .statusCode(apiResponse.getStatus())
                    .message(apiResponse.getMessage())
                    .build();

            return Violation.builder()
                    .header(header)
                    .build();
        }

        // Default response if no field errors are present
        return Violation.builder()
                .header(Violation.Header.builder()
                        .statusCode(APIResponse.BAD_REQUEST.getStatus())
                        .message("Validation error")
                        .build())
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse handleBindException(BindException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    Violation.builder()
                            .header(Violation.Header.builder()
                                    .statusCode(fieldError.getField())
                                    .message(fieldError.getDefaultMessage())
                                    .build())
                            .build());
        }
        return error;
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ValidationErrorResponse handleException(Exception e, HttpServletRequest request) {

        if ("/favicon.ico".equals(request.getRequestURI())) {
            log.warn("Error occurred while handling favicon request: {}", e.getMessage());
        } else {
            // Log the full stack trace for other exceptions
            log.error("UNKNOWN Error ", e);
        }
        APIResponse responseCode =
                APIResponse.INTERNAL_SERVER_ERROR;
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(
                Violation.builder()
                        .header(Violation.Header.builder()
                                .statusCode(responseCode.getStatus())
                                .message(responseCode.getMessage())
                                .build())
                        .build());
        return error;
    }
}
