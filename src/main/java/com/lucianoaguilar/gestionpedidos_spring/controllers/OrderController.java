package com.lucianoaguilar.gestionpedidos_spring.controllers;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Order;
import com.lucianoaguilar.gestionpedidos_spring.models.entities.Product;
import com.lucianoaguilar.gestionpedidos_spring.models.entities.User;
import com.lucianoaguilar.gestionpedidos_spring.services.OrderService;
import com.lucianoaguilar.gestionpedidos_spring.services.ProductService;
import com.lucianoaguilar.gestionpedidos_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        List<Product> products = new ArrayList<>();
        try {
            if (order == null || order.getCreatedBy() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userService.getUserById(order.getCreatedBy().getId()).get();
            order.setCreatedBy(user);
            for (Product product : order.getProducts()) {
                if (product.getId() != null) {
                    products.add(productService.getProductById(product.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID")));
                }
            }
            order.setProducts(products);
            order.setTotal();
            Order newOrder = orderService.saveOrder(order);

            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<Order>> getOrders(){
        List<Order> orders = orderService.getOrders();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id){
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()){
            User user = order.get().getCreatedBy();
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
