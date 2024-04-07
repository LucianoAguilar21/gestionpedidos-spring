package com.lucianoaguilar.gestionpedidos_spring.repositories;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);


}
