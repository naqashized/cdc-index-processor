package com.techphile.userIndex.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PayLoad(UserMessage before, UserMessage after, Operation op) {
}