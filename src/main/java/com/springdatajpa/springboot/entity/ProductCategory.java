package com.springdatajpa.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;
    private String categoryDescription;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
