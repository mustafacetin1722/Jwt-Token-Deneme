package com.mustafa.security.jwttokendeneme.dto;

public record AuthRequest(
        String username,
        String password
) {
}
