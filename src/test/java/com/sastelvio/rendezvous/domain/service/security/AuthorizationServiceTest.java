package com.sastelvio.rendezvous.domain.service.security;

import com.sastelvio.rendezvous.api.v1.dto.security.AuthenticationDTO;
import com.sastelvio.rendezvous.api.v1.dto.security.RegisterDTO;
import com.sastelvio.rendezvous.config.security.TokenService;
import com.sastelvio.rendezvous.domain.entity.security.Role;
import com.sastelvio.rendezvous.domain.entity.security.User;
import com.sastelvio.rendezvous.domain.repository.security.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void testLoginSuccess() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        Authentication authentication = mock(Authentication.class);
        when(applicationContext.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new User(username, password, null, null, null, null, null, null, null, null, null, null, null));
        when(tokenService.generateToken(any(User.class))).thenReturn("testToken");

        // Act
        ResponseEntity<Object> responseEntity = authorizationService.login(new AuthenticationDTO(username, password));

        // Assert
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(any(User.class));
    }

    @Test
    public void testRegisterSuccess() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "testUser",
                "Test",
                "User",
                "test@example.com",
                0,
                "Test Abous",
                "Test Location",
                "Test Linkedlin",
                "Test Facebook",
                "Test Twitter",
                "Test Instagram",
                "testPassword",
                Role.USER);
        User user = new User(
                registerDTO.username(),
                registerDTO.password(),
                registerDTO.lastName(),
                registerDTO.email(),
                registerDTO.phone(),
                registerDTO.about(),
                registerDTO.location(),
                registerDTO.link_linkedin(),
                registerDTO.link_facebook(),
                registerDTO.link_twitter(),
                registerDTO.link_instagram(),
                new BCryptPasswordEncoder().encode(registerDTO.password()),
                Role.USER);
        when(userRepository.findByUsername(registerDTO.username())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(user);

        // Act
        ResponseEntity<Object> responseEntity = authorizationService.register(registerDTO);

        // Assert
        verify(userRepository, times(1)).findByUsername(registerDTO.username());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testRegisterDuplicateUsername() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "testUser",
                "Test",
                "User",
                "test@example.com",
                0,
                "Test Abous",
                "Test Location",
                "Test Linkedlin",
                "Test Facebook",
                "Test Twitter",
                "Test Instagram",
                "testPassword",
                Role.USER);
        when(userRepository.findByUsername(registerDTO.username()))
                .thenReturn(new User(registerDTO.username(), "", "", "", 0, "", "", "", "", "", "", "", Role.USER));

        // Act
        ResponseEntity<Object> responseEntity = authorizationService.register(registerDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(
                new User(username, new BCryptPasswordEncoder().encode("testPassword"), null, null, null, null, null, null, null, null, null, null, Role.USER));

        // Act
        UserDetails userDetails = authorizationService.userDetailsService().loadUserByUsername(username);

        // Assert
        verify(userRepository, times(1)).findByUsername(username);
    }

}