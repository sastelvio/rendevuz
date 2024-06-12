package com.sastelvio.rendezvous.api.v1.controller.security;

import com.sastelvio.rendezvous.api.v1.dto.request.PatientRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.PatientResponse;
import com.sastelvio.rendezvous.api.v1.dto.security.AuthenticationDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.RegisterDTO;
import com.sastelvio.rendezvous.config.security.TokenBlacklistService;
import com.sastelvio.rendezvous.config.security.TokenService;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.entity.security.User;
import com.sastelvio.rendezvous.domain.repository.security.UserRepository;
import com.sastelvio.rendezvous.domain.service.security.AuthorizationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
        return authorizationService.register(registerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody RegisterDTO updateDTO) {
        return authorizationService.update(id, updateDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return authorizationService.login(authenticationDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal User userDetails) {
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            // User not found
            return ResponseEntity.notFound().build();
        }
        // Remove sensitive information if needed before returning
        user.setPassword(null);
        System.out.println(user.getEmail());
        return ResponseEntity.ok(user);
    }

}
