package com.lucianoaguilar.gestionpedidos_spring.repositories;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    public Category findByName(String categoryName);
}
