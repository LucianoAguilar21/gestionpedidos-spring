package com.lucianoaguilar.gestionpedidos_spring.controllers;

import com.lucianoaguilar.gestionpedidos_spring.exceptions.AuthenticationException;
import com.lucianoaguilar.gestionpedidos_spring.models.AuthenticationResponse;
import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import com.lucianoaguilar.gestionpedidos_spring.repositories.UserRepository;
import com.lucianoaguilar.gestionpedidos_spring.services.AuthenticationService;
import com.lucianoaguilar.gestionpedidos_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        //logger.debug("Attempting login for user: {}", request.getUsername());

        userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        AuthenticationResponse authenticationResponse = authService.authenticate(request);
        if (authenticationResponse.getToken() == null || authenticationResponse.getToken().isEmpty()) {
            throw new AuthenticationException("Invalid username or password");
        }

        //logger.debug("Login successful for user: {}", request.getUsername());
        return ResponseEntity.ok(authenticationResponse);
    }



}
