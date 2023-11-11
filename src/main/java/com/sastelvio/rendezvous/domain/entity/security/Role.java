package com.sastelvio.rendezvous.domain.entity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("admin"),
    USER("user"),
    PATIENT("patient");

    private String role;

}