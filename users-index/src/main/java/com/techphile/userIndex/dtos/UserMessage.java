package com.techphile.userIndex.dtos;

public record UserMessage(
        Long id,
        String name,
        String email,
        String phone
) {

}
