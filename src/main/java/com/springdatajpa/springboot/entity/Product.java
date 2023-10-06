package com.springdatajpa.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        name = "products",
        schema = "ecommerce",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "sku_unit",
                        columnNames = "stock_keeping_unit"
                )
        }
)
public class Product {
    /**
     * 4 primary key generation strategies
     * 1.GenerationType.AUTO
     *      the is default generation type and lets persistence provider choose the generation strategy.
     *      if you use Hibernate as your persistence provider, it selects a generation strategy based on the
     *      database-specific dialect.
     *      if we are using auto it will create a separate table by name sequence  in the database.
     * 2.GenerationType.IDENTITY
     *      it relies on an auto-incremented database column and lets the database generate a new value
     *      with each insert operation.
     *      from a database point of view, this is very efficient because the auto-increment columns are highly
     *      optimized, and it doesn't require any additional statements.
     *      Not good for JDBC batch operations
     * 3.GenerationType.SEQUENCE
     *      is to generate primary key values and uses a database sequence to generate unique values,
     *      it requires additional select statements to get the next value from a database sequence.
     *      But this has no performance impact on most applications.
     *      this @SequenceGenerator annotation lets you define the name of the generator, the name, and schema of the database sequence
     *      and allocation size of the sequence.
     * 4.GenerationType.TABLE
     *      gets only rarely used nowdays
     *      it simulates a sequence by storing and updating its current value in a database table which
     *      requires the use of pessimistic locks which put all transactions into a sequential order.
     *      This slows down your application, and you should, therefore, prefer the GenerationType.SEQUENCE, if your
     *      database supports sequences, which most popular databases do.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_keeping_unit",nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;

    private boolean active;

    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

}
