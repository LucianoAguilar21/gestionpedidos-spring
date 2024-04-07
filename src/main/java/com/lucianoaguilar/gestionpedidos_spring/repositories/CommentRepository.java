package com.lucianoaguilar.gestionpedidos_spring.repositories;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
