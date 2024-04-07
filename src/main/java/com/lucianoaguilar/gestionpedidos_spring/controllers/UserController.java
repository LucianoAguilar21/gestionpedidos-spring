package com.lucianoaguilar.gestionpedidos_spring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Demo hello");
    }
}
