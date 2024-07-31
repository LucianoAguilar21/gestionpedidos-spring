package com.lucianoaguilar.gestionpedidos_spring.services;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Category;
import com.lucianoaguilar.gestionpedidos_spring.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category saveCategory(Category category){
        return this.categoryRepository.save(category);
    }

    public List<Category> findCategories(){
        return  this.categoryRepository.findAll();
    }

    public Category findCategory(String categoryName){
        return this.categoryRepository.findByName(categoryName);
    }

}
