package com.sastelvio.rendezvous.api.v1.controller.security;

import com.sastelvio.rendezvous.api.v1.dto.security.AuthenticationDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.RegisterDTO;
import com.sastelvio.rendezvous.config.security.TokenBlacklistService;
import com.sastelvio.rendezvous.config.security.TokenService;
import com.sastelvio.rendezvous.domain.service.security.AuthorizationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO){
        return authorizationService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return authorizationService.login(authenticationDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok().build();
    }  

}
