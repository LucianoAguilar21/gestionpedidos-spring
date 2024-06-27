package com.lucianoaguilar.gestionpedidos_spring.services;

import com.lucianoaguilar.gestionpedidos_spring.models.AuthenticationResponse;
import com.lucianoaguilar.gestionpedidos_spring.models.Token;
import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import com.lucianoaguilar.gestionpedidos_spring.repositories.TokenRepository;
import com.lucianoaguilar.gestionpedidos_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
        logger.debug("Attempting to register user: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("User already exists: {}", request.getUsername());
            return new AuthenticationResponse(null, "User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        logger.debug("User registration successful: {}", user.getUsername());
        return new AuthenticationResponse(jwt, "User registration was successful");
    }

    public AuthenticationResponse authenticate(User request) {
        logger.debug("Attempting to authenticate user: {}", request.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", request.getUsername());
            throw new AuthenticationException("Invalid username or password") {};
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtService.generateToken(user);
        revokeAllTokensByUser(user);
        saveUserToken(jwt, user);

        logger.debug("User login successful: {}", user.getUsername());
        return new AuthenticationResponse(jwt, "User login was successful");
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> t.setLoggedOut(true));
        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
