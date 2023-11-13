package com.sastelvio.rendezvous.api.v1.controller.security;

import com.sastelvio.rendezvous.api.v1.dto.security.AuthenticationDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.RegisterDTO;
import com.sastelvio.rendezvous.domain.service.security.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return authorizationService.login(authenticationDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO){
        return authorizationService.register(registerDTO);
    }

}
