package com.spring.boilercode.controller.ReqResDTO;

import com.spring.boilercode.customValidator.NotEmptyField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.Data;

import java.util.Map;
import org.hibernate.validator.constraints.UUID;

@Data
@Schema(title = "General Request Data ", description = "General request data class with various validations.")
public class GeneralRequest {

    @NotBlank(message = "FIELD_REQUIRED")
    @UUID(message = "INVALID_UUID_FORMAT")
    @Schema(description = "Unique identifier for the request.", example = "8964d3ef-c99b-4ea5-a806-bc478e7b02ed")
    private String requestId;

    @NotNull(message = "SERVICE_IDENTIFIER_REQUIRED")
    @NotEmptyField(message = "EMPTY_SERVICE_IDENTIFIER")
    @Schema(description = "Service identifier for the request.", example = "service_001")
    private String serviceId;

    @NotNull(message = "TIMESTAMP_REQUIRED")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", message = "INVALID_TIMESTAMP_FORMAT")
    @Schema(description = "Timestamp of the request creation.", example = "2024-08-26T13:34:32.132Z")
    private String timestamp;

    @NotNull(message = "USER_INFO_REQUIRED")
    @Size(min = 1, message = "USER_INFO_NOT_EMPTY")
    @Schema(description = "User-specific data for the request.")
    private Map<String, String> userInfo;

    @NotNull(message = "REQUEST_DETAILS_REQUIRED")
    @Schema(description = "Detailed information about the request.")
    private Map<String, String> requestDetails;

    @Email(message = "INVALID_EMAIL_FORMAT")
    @Schema(description = "Email address associated with the request.", example = "user@example.com")
    private String email;

    @Size(min = 6, max = 12, message = "INVALID_PHONE_NUMBER_LENGTH")
    @Pattern(regexp = "^[0-9]{6,12}$", message = "INVALID_PHONE_NUMBER_FORMAT")
    @Schema(description = "Phone number associated with the request.", example = "1234567890")
    private String phoneNumber;

    @Min(value = 1, message = "MIN_VALUE_EXCEEDED")
    @Max(value = 100, message = "MAX_VALUE_EXCEEDED")
    @Schema(description = "Amount associated with the request. Must be between 1 and 100.", example = "50")
    private Integer amount;

    @AssertTrue(message = "CONDITIONS_NOT_ACCEPTED")
    @Schema(description = "Flag to indicate if terms and conditions are accepted.")
    private Boolean acceptedTermsAndConditions;

    private Instant updateTimestamp;




}
