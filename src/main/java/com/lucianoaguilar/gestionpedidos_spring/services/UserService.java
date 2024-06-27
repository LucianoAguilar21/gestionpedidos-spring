package com.lucianoaguilar.gestionpedidos_spring.services;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import com.lucianoaguilar.gestionpedidos_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);

    }
}
