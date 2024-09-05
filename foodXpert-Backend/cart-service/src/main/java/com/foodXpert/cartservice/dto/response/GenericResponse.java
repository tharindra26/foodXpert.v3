package com.foodXpert.cartservice.dto.response;

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
