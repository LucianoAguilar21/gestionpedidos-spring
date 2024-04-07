package com.lucianoaguilar.gestionpedidos_spring.repositories;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
