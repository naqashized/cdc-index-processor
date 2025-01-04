package com.techphile.userIndex.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserPayload(PayLoad payload) {
}
