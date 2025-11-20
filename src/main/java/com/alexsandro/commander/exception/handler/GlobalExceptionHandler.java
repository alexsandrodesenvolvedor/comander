package com.alexsandro.commander.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.metadata.ConstraintDescriptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.util.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    public static final String VALIDATION_FAILED = "Os campos informados nao passaram nos criterios de validacao";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_GROUPS = "groups";
    public static final String KEY_PAYLOAD = "payload";
    private final Validator validator;

    @Value("${app.debug:false}")
    private boolean debugMode;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = status.getReasonPhrase();

        Object target = ex.getBindingResult().getTarget();
        Set<ConstraintViolation<Object>> violations = target != null ? validator.validate(target) : Collections.emptySet();

        List<FieldValidationError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> toFieldValidationError(fe, violations))
                .toList();

        String detail = debugMode ? getStackTrace(ex) : ex.getClass().getName();

        ErrorResponse body = new ErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                error,
                VALIDATION_FAILED,
                request.getRequestURI(),
                detail,
                fieldErrors
        );

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus != null) ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR;
        String error = (responseStatus != null && !responseStatus.reason().isEmpty()) ?
                responseStatus.reason() : status.getReasonPhrase();

        String detail = debugMode ? getStackTrace(ex) : null;

        ErrorResponse body = new ErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                error,
                ex.getMessage(),
                request.getRequestURI(),
                detail,
                null
        ) {
        };

        return new ResponseEntity<>(body, status);
    }

    private FieldValidationError toFieldValidationError(FieldError fe, Set<ConstraintViolation<Object>> violations) {
        Map<String, Object> attrs = null;
        Optional<ConstraintViolation<Object>> match = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals(fe.getField()))
                .findFirst();

        if (match.isPresent()) {
            ConstraintDescriptor<?> descriptor = match.get().getConstraintDescriptor();
            attrs = new HashMap<>(descriptor.getAttributes());
            attrs.remove(KEY_MESSAGE);
            attrs.remove(KEY_GROUPS);
            attrs.remove(KEY_PAYLOAD);
        }

        Object rejected = fe.getRejectedValue();
        return new FieldValidationError(fe.getField(), rejected, fe.getDefaultMessage(), attrs);
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}