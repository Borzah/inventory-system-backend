package com.demo.inventory.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;
}
