package com.bfhl.exception;

import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private BfhlService bfhlService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(500).body(BfhlResponse.builder()
                .isSuccess(false)
                .officialEmail(bfhlService.getOfficialEmail())
                .data(ex.getMessage())
                .build());
    }
}
