package com.lucianoaguilar.gestionpedidos_spring.controllers;

import com.lucianoaguilar.gestionpedidos_spring.models.AuthenticationResponse;
import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import com.lucianoaguilar.gestionpedidos_spring.repositories.UserRepository;
import com.lucianoaguilar.gestionpedidos_spring.services.AuthenticationService;
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
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {

        if(userRepository.findByUsername(request.getUsername()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse(null, "Usuario no encontrado"));
        }

        AuthenticationResponse authenticationResponse = authService.authenticate(request);
        if (authenticationResponse.getToken() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthenticationResponse(null, "Usuario no encontrado"));
        } else {

            return ResponseEntity.ok(authenticationResponse);
        }
    }


}
