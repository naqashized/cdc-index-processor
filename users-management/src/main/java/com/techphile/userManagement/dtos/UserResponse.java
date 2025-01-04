package com.techphile.userManagement.dtos;

import com.techphile.userManagement.domain.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone
) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
