package com.sastelvio.rendezvous.api.v1.dto.security;

public record LoginResponseDTO(String token, String id, String firstName, String lastName, String email, String role) {
}
