package com.lucianoaguilar.gestionpedidos_spring.controllers;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Comment;
import com.lucianoaguilar.gestionpedidos_spring.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){
        Comment newComment = commentService.saveComment(comment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

}
