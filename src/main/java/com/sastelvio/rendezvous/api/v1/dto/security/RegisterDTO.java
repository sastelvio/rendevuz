package com.sastelvio.rendezvous.api.v1.dto.security;

import com.sastelvio.rendezvous.domain.entity.security.Role;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotNull String username,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        Integer phone,
        String about,
        String location,
        String link_linkedin,
        String link_facebook,
        String link_twitter,
        String link_instagram,
        @NotNull String password,
        @NotNull Role role) {
}
