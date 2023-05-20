package com.in28minutes.webservices.restfulwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
}
