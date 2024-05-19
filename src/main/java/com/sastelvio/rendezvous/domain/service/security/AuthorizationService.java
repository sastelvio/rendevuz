package com.sastelvio.rendezvous.domain.service.security;

import com.sastelvio.rendezvous.api.v1.dto.security.AuthenticationDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.LoginResponseDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.RegisterDTO;
import com.sastelvio.rendezvous.config.security.TokenService;
import com.sastelvio.rendezvous.domain.entity.security.User;
import com.sastelvio.rendezvous.domain.repository.security.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthorizationService implements UserService {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;


    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        log.info("authentication a session: {}", data.username());
        authenticationManager = context.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(
            new LoginResponseDTO(
                token, 
                user.getId().toString(), 
                user.getFirstName(), 
                user.getLastName(), 
                user.getEmail(), 
                user.getRole().getRole()
            )
        );
    }

    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO){
        log.info("registering user: {}", registerDTO.username());
        if(this.userRepository.findByUsername(registerDTO.username()) != null){ log.error("username exists already!"); return ResponseEntity.badRequest().build();}
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.username(), registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), encryptedPassword, registerDTO.role());
        newUser.setDateCreation(LocalDateTime.now());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsername(username);
            }
        };
    }
}
