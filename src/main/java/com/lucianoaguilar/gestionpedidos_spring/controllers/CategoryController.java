package com.lucianoaguilar.gestionpedidos_spring.controllers;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Category;
import com.lucianoaguilar.gestionpedidos_spring.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody  Category category){
        Category newCategory = categoryService.saveCategory(category);
        if(newCategory != null){
            return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = this.categoryService.findCategories();
        if (categories != null){
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategory(@PathVariable String name){
        Category category= this.categoryService.findCategory(name);
        if (category != null){
            return new ResponseEntity<>(category,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
