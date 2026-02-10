package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BfhlResponse {
    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("official_email")
    private String officialEmail;

    private Object data;
}
