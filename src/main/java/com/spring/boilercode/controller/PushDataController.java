package com.spring.boilercode.controller;

import com.spring.boilercode.controller.ReqResDTO.GeneralRequest;
import com.spring.boilercode.controller.ReqResDTO.GeneralResponse;
import com.spring.boilercode.controller.application.ServiceInterface;
import com.spring.boilercode.enums.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "General Push Data Requests")
public class PushDataController {

    private final ServiceInterface serviceInterface;

    @Autowired
    public PushDataController(ServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Operation(summary = "Process a new general request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "This API processes the request.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralResponse.class)) }),

            @ApiResponse(responseCode = "400", description = "Invalid values provided",
                    content = @Content),

            @ApiResponse(responseCode = "500", description = "Unknown Internal Error.",
                    content = @Content) })
    @PostMapping("/process-request")
    public ResponseEntity<GeneralResponse> processRequest(@Valid @RequestBody GeneralRequest req) {
        // Assuming you have a general service for processing requests
        serviceInterface.processRequest(req);

        return new ResponseEntity<>(GeneralResponse.builder()
                .header(GeneralResponse.Header.builder()
                        .statusCode(APIResponse.SUCCESS.getStatus())
                        .message(APIResponse.SUCCESS.getMessage())
                        .build())
                .build(), HttpStatus.OK);
    }
}


