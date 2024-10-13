package com.spring.boilercode.enums;

import lombok.Getter;

/**
 * Enum to store the REST API responses.
 */
@Getter
public enum APIResponse {

    // Success messages
    SUCCESS("EF000", "Request processed successfully"),
    API_SUCCESS("EF000", "API call is successful"),

    // Validation errors
    FIELD_REQUIRED("EF001", "This field is required"),
    INVALID_UUID_FORMAT("EF002", "The requestId must be a valid UUID"),
    SERVICE_IDENTIFIER_REQUIRED("EF003", "Service identifier is required"),
    EMPTY_SERVICE_IDENTIFIER("EF004", "Service identifier cannot be empty"),
    TIMESTAMP_REQUIRED("EF005", "Timestamp is required"),
    INVALID_TIMESTAMP_FORMAT("EF006", "Timestamp format is invalid. Expected format: YYYY-MM-DDTHH:MM:SS.sssZ"),
    USER_INFO_REQUIRED("EF007", "User information is required"),
    USER_INFO_NOT_EMPTY("EF008", "User information cannot be empty"),
    REQUEST_DETAILS_REQUIRED("EF009", "Request details are required"),
    INVALID_EMAIL_FORMAT("EF010", "Email format is invalid"),
    INVALID_PHONE_NUMBER_FORMAT("EF011", "Phone number format is invalid"),
    INVALID_PHONE_NUMBER_LENGTH("EF012", "Phone number must be between 6 and 12 digits"),
    MIN_VALUE_EXCEEDED("EF013", "The amount must be at least 1"),
    MAX_VALUE_EXCEEDED("EF014", "The amount cannot exceed 100"),
    CONDITIONS_NOT_ACCEPTED("EF015", "Terms and conditions must be accepted"),
    INVALID_IDENTIFIER_FORMAT("EF016", "Identifier format is invalid. Valid format: XXXX-XXXXXXX"),

    // General errors
    BAD_REQUEST("EF017", "Bad request"),
    INTERNAL_SERVER_ERROR("EF018", "Internal server error");

    final private String message;
    final private String status;

    APIResponse( String status, String msg) {
        this.message = msg;
        this.status = status;
    }

}
