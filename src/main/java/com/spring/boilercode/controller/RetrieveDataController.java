package com.spring.boilercode.controller;

import com.spring.boilercode.controller.ReqResDTO.*;
import com.spring.boilercode.controller.application.ServiceInterface;
import com.spring.boilercode.enums.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The controller for general API requests.
 */
@Validated
@RestController
@RequestMapping(value = "/api/")
@Tag(name = "General Retrieve Data Requests")
public class RetrieveDataController {

    private final ServiceInterface serviceInterface;

    @Autowired
    public RetrieveDataController(ServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Operation(summary = "Get General Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This API will get general data.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content)
    })
    @GetMapping("/get-general-data/{identifier}")
    public ResponseEntity<GeneralResponse> getGeneralData(
            @Pattern(regexp = "^\\d{4}-\\d{7}$", message = "INVALID_IDENTIFIER_FORMAT")
            @PathVariable("identifier") String identifier
    ) {
        return new ResponseEntity<>(GeneralResponse.builder()
                .header(GeneralResponse.Header.builder()
                        .statusCode(APIResponse.API_SUCCESS.getStatus())
                        .message(APIResponse.API_SUCCESS.getMessage())
                        .build())
                .body(GeneralResponse.FixedBody.builder()
                        .name("John Doe")
                        .phone(identifier)
                        .email("john.doe@example.com")
                        .userType("Regular")
                        .build())
                .build(), HttpStatus.OK);
    }
    @Operation(summary = "Get General Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This API will get general data.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Error.", content = @Content)
    })
    @GetMapping("/get-dynamic-data/{identifier}")
    public ResponseEntity<GeneralResponse> getDynamicData(
            @Pattern(regexp = "^\\d{4}-\\d{7}$", message = "INVALID_IDENTIFIER_FORMAT")
            @PathVariable("identifier") String identifier
    ) {

        return new ResponseEntity<>(GeneralResponse.builder()
                .header(GeneralResponse.Header.builder()
                        .statusCode(APIResponse.API_SUCCESS.getStatus())
                        .message(APIResponse.API_SUCCESS.getMessage())
                        .build())
                .body(GeneralResponse.DynamicBody.builder()
                        .userData(Map.of(
                                "identifier", identifier,
                                "firstName", "John",  // Mock first name
                                "lastName", "Doe",    // Mock last name
                                "age", "30",         // Mock age
                                "email", "john.doe@example.com", // Mock email
                                "phone", "1234567890",  // Mock phone number
                                "address", "123 Main Street, Springfield"  // Mock address
                        ))
                        .build())
                .build(), HttpStatus.OK);
    }
}


