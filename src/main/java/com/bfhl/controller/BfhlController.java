package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BfhlController {

    @Autowired
    private BfhlService bfhlService;

    @GetMapping("/health")
    public ResponseEntity<BfhlResponse> getHealth() {
        return ResponseEntity.ok(BfhlResponse.builder()
                .isSuccess(true)
                .officialEmail(bfhlService.getOfficialEmail())
                .build());
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> handleBfhl(@RequestBody BfhlRequest request) {
        Object data = null;

        if (request.getFibonacci() != null) {
            data = bfhlService.getFibonacci(request.getFibonacci());
        } else if (request.getPrime() != null) {
            data = bfhlService.getPrimes(request.getPrime());
        } else if (request.getLcm() != null) {
            data = bfhlService.getLcm(request.getLcm());
        } else if (request.getHcf() != null) {
            data = bfhlService.getHcf(request.getHcf());
        } else if (request.getAI() != null) {
            data = bfhlService.getAiResponse(request.getAI());
        } else {
            return ResponseEntity.badRequest().body(BfhlResponse.builder()
                    .isSuccess(false)
                    .officialEmail(bfhlService.getOfficialEmail())
                    .data("Invalid request: exactly one functional key required")
                    .build());
        }

        return ResponseEntity.ok(BfhlResponse.builder()
                .isSuccess(true)
                .officialEmail(bfhlService.getOfficialEmail())
                .data(data)
                .build());
    }
}
