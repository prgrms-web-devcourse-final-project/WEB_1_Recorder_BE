package com.revup.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    USER("ROLE_USER");

    private final String role;

    public static UserRole findByRole(final String role) {
        return Arrays.stream(UserRole.values())
                .filter(userRole -> userRole.role.equals(role))
                .findFirst()
                .orElse(null);
    }
}
