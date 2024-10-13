package com.spring.boilercode.controller.ReqResDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(title = "General Response Data", description = "General response structure for request.")
public class GeneralResponse {

    @Schema(description = "Response header containing status information.")
    private Header header;
    @Schema(description = "Body containing general data.")
    private Body body;

    @Data
    @Builder
    @Schema(title = "Header", description = "Header containing status code and message.")
    public static class Header {

        @Schema(description = "Status code of the response.", example = "EF000")
        private String statusCode;

        @Schema(description = "Message describing the status.", example = "Request processed successfully")
        private String message;
    }

    public static interface Body {
    }

    @Data
    @Builder
    @Schema(title = "FixedBody", description = "Body containing general fixed data.")
    public static class FixedBody implements Body {

        @Schema(description = "Name associated with the request.", example = "John Doe")
        private String name;

        @Schema(description = "Phone number associated with the request.", example = "1234567890")
        private String phone;

        @Schema(description = "Email address associated with the request.", example = "john.doe@example.com")
        private String email;

        @Schema(description = "Type of customer or user.", example = "Regular / Partner")
        private String userType;
    }
    @Data
    @Builder
    @Schema(title = "DynamicBody", description = "Body containing dynamic data, such as user data.")
    public static class DynamicBody implements Body {

        @Schema(description = "Body containing dynamic data.")
        private Map<String, String> userData;
    }
}