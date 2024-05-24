package com.toggersapi.ToggersAPICore.Domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.toggersapi.ToggersAPICore.Infrastructure.DTO.LoginRequest;
import com.toggersapi.ToggersAPICore.Infrastructure.Entities.User;
import com.toggersapi.ToggersAPICore.Infrastructure.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.NoSuchElementException;

public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    Algorithm algorithm = Algorithm.HMAC256("biscoito");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Email not found"));
    }

    // TODO: exception catching
    public String generateToken(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new NoSuchElementException("Email not found"));

        return JWT.create()
                .withIssuer("API-AUTH")
                .withSubject(user.getEmail())
                .withExpiresAt(
                        LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.UTC)
                ).sign(algorithm);
    }

    public String validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("API-AUTH")
                .build()
                .verify(token)
                .getSubject();
    }

}
