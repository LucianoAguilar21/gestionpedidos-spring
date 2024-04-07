package com.lucianoaguilar.gestionpedidos_spring.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String client;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @Column(nullable = false)
    private boolean delivery;

    @Column()
    private LocalDate deliveryDate;

    @Column()
    private String address;

    @Column()
    private Double deliveryCost;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private boolean paid;

    @ManyToOne
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns ={
                    @JoinColumn(name = "order_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id")
            }
    )
    private List<Product> products;

    @OneToMany
    private List<Comment> comments;

    public Double getTotal(){
        Double sum = 0.0;
        if(!this.getProducts().isEmpty()){
            for (Product product : this.getProducts()){
                sum += product.getPrice();
            }
        }

        this.total = sum + deliveryCost;
        return this.total;
    }

    public void setTotal(){
        Double sum = 0.0;
        if(!this.getProducts().isEmpty()){
            for (Product product : this.getProducts()){
                sum += product.getPrice();
            }
        }

        this.total = sum + deliveryCost;

    }



}
