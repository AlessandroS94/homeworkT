package it.telespazio.work.controller;

import it.telespazio.work.dto.response.FilePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(FileControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("Generic Error", e);
        return ResponseEntity.badRequest().body(new FilePayload("Status","Generic Error"));
    }
}
