package com.lucianoaguilar.gestionpedidos_spring.services;

import com.lucianoaguilar.gestionpedidos_spring.models.entities.Order;
import com.lucianoaguilar.gestionpedidos_spring.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Integer id){
        return orderRepository.findById(id);
    }

    public Optional<Order> updateOrder(Integer id, Order request){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            order.get().setDescription(request.getDescription());
            order.get().setClient(request.getClient());
            order.get().setCategory(request.getCategory());
            order.get().setDelivery(request.isDelivery());
            order.get().setDeliveryDate(request.getDeliveryDate());
            order.get().setAddress(request.getAddress());
            order.get().setDeliveryCost(request.getDeliveryCost());
            order.get().setTotal(request.getTotal());
            order.get().setStatus(request.getStatus());
            order.get().setPaid(request.isPaid());
            order.get().setProducts(request.getProducts());
            order.get().setComments(request.getComments());
            orderRepository.save(order.get());
        }

        return order;

    }

}
