package com.alexsandro.commander.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidationError {

    private String field;
    private Object rejectedValue;
    private String message;
    private Map<String, Object> constraints;

}