package com.spring.boilercode.handler;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Schema(title = "Error Response", description = "The response structure for validation errors.")
public class Violation {

    @Schema(description = "Response header containing status code and message.")
    private Header header;

    @Data
    @Builder
    @Schema(title = "Header", description = "Header containing status code and message.")
    public static class Header {

        @Schema(description = "Status code of the response.", example = "EF001")
        private String statusCode;

        @Schema(description = "Message describing the status.", example = "Id cannot be empty")
        private String message;
    }
}