package com.foodXpert.productservice.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
}
