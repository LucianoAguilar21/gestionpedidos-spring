package com.lucianoaguilar.gestionpedidos_spring.services;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Product;
import com.lucianoaguilar.gestionpedidos_spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id){
        return productRepository.findById(id);
    }

    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    public Optional<Product> updateProduct(Integer id, Product request){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            product.get().setName(request.getName());
            product.get().setPrice(request.getPrice());
            productRepository.save(product.get());
        }
        return product;
    }

}
