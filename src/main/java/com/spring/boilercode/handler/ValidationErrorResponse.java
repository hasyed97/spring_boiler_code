package com.spring.boilercode.handler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

}
