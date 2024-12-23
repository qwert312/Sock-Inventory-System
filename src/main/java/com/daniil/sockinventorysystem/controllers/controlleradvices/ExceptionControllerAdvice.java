package com.daniil.sockinventorysystem.controllers.controlleradvices;

import com.daniil.sockinventorysystem.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(
                String.format("The passed value %s of parameter %s cannot be converted to %s type.",
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName()));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<String> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("The request body cannot be deserialized to required type.");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        return ResponseEntity.badRequest().body("Error occurred during file upload: " + ex.getMessage());
    }

    @ExceptionHandler(CSVFileProcessingException.class)
    public ResponseEntity<String> CSVFileProcessingExceptionHandler(CSVFileProcessingException ex) {
        return ResponseEntity.badRequest().body("Error occurred during file processing: " + ex.getMessage());
    }

    @ExceptionHandler(NotEnoughSocksException.class)
    public ResponseEntity<String> NotEnoughSocksExceptionHandler(NotEnoughSocksException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(SocksDoesNotExistsException.class)
    public ResponseEntity<String> SocksDoesNotExistsExceptionHandler(SocksDoesNotExistsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> ValidationExceptionHandler(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<String> MappingExceptionHandler(MappingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
