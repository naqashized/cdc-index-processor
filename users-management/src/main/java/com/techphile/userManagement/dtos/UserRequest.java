package com.techphile.userManagement.dtos;

import com.techphile.userManagement.domain.User;

public record UserRequest(
    String name,
    String email,
    String phone
) {
    public User toUser() {
        return new User(null, name, email, phone);
    }
}
