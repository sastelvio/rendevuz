package com.sastelvio.rendezvous.domain.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 45)
    private String firstName;
    @NotBlank
    @Size(max = 45)
    private String lastName;
    @NotBlank
    @Size(max = 200)
    private String email;
    @Size(max = 20)
    private Integer phone;
    private String about;
    @Size(max = 100)
    private String location;
    @Size(max = 255)
    private String link_linkedin;
    @Size(max = 255)
    private String link_facebook;
    @Size(max = 255)
    private String link_twitter;
    @Size(max = 255)
    private String link_instagram;
    @Size(min = 6, max = 255)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    @Column(name = "date_update", nullable = false)
    private LocalDateTime dataUpdate;

    public User(
            String username,
            String firstName,
            String lastName,
            String email,
            Integer phone,
            String about,
            String location,
            String link_linkedin,
            String link_facebook,
            String link_twitter,
            String link_instagram,
            String password,
            Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.about = about;
        this.location = location;
        this.link_linkedin = link_linkedin;
        this.link_facebook = link_facebook;
        this.link_twitter = link_twitter;
        this.link_instagram = link_instagram;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == role.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_PATIENT"));
        else if (this.role == role.USER)
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_PATIENT"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}