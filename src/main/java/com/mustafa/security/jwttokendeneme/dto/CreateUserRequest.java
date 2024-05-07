package com.mustafa.security.jwttokendeneme.dto;

import com.mustafa.security.jwttokendeneme.model.Role;
import lombok.Builder;

import java.util.Set;
@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
) {
}
