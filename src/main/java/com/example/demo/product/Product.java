package com.example.demo.product;

import com.example.demo.category.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    private float price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    Product(){
        
    }

    Product(String name, String description, float price, Category category){
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
