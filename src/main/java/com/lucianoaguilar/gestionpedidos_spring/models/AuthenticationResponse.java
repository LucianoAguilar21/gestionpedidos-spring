package com.lucianoaguilar.gestionpedidos_spring.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private String token;
    private String message;


}
