package com.lucianoaguilar.gestionpedidos_spring.exceptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String msg){
        super(msg);
    }
}
