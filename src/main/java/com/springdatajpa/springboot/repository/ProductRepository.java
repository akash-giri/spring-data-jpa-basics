package com.springdatajpa.springboot.repository;

import com.springdatajpa.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Query method structure
     * findBy -->QueryMethod Subject Keyword ,NameAndDescription--> is a query method predicateKeyword
     * @param name  ->method parameters
     * @param description  ->method parameters
     * @return QueryMethod return type List<Product>
     *
     * Rules to create query methods
     * 1. the name of our query method must start with one of the following prefixes :
     *    find...By,read...By,query..By,count...By and get...By.
     *    Examples: findByName, readByName,queryByName,getByName
     * 2. if u want to limit the number of returned query results, we can add te First or the Top keyword
     *    before the first by word.
     *    examples: findFirstByName, readFirst2ByName,findTop10ByName
     * 3. if we want to select unique results, we have to add the Distinct Keyword before the first By word.
     *    Examples: findDistinctByName or findNameDistinctBy
     * 4. Combine property expression with AND or OR.
     *    Examples: findByNameOrDescription, findByNameAndDescription
     *
     * Returning Values From query methods
     * A query method can return only one result or more than one result.
     * 1. if we writing a query that should return only one result, we can return the following types:
     *   Basic type. Our query method will return the found basic type or null.
     *   Entity . Our query method will return an entity object or null.
     *   Guava/Java 8 Optional<T> . Our query method will retun an Optional that contains the found
     *      object or an empty Optional.
     *       public Product findByName(String name);
     *       public Optional<Product> findById(long id);
     * 2. if we writing a query method that should return more than one result, we can return the following
     *    types:
     *    List<T>. Our query method will return a list that contains the query results or an empty list.
     *    Stream<T>. Our query method will return a Stream that can be used to access the query results
     *    or an empty Stream.
     *        List<Product> findByPriceGreaterThan(BigDecimal Price);
     *        Stream<Product> findByPriceLessThan(BigDecimal Price);
     *
     *
     */
//    List<Product> findByNameAndDescription(String name, String description);

    /**
     * Return the found product entry by using its name as search criteria. If no product entry is found,
     * this method returns null.
     */
    Product findByName(String name);

    /**
     * Returns an Optional which contains the found product entry by using its id as search criteria. If
     * no product entry is found, this method returns an empty Optional
     */
    Optional<Product> findById(Long id);
    
    List<Product> findByNameOrDescription(String name,String description);

    List<Product> findByNameAndDescription(String s, String s1);

    Product findDistinctByName(String s);

    List<Product> findByPriceGreaterThan(BigDecimal bigDecimal);

    List<Product> findByPriceLessThan(BigDecimal bigDecimal);

    List<Product> findByNameContaining(String s);

    List<Product> findByNameLike(String s);

    List<Product> findByPriceBetween(BigDecimal bigDecimal, BigDecimal bigDecimal1);

    List<Product> findByDateCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Product> findByNameIn(List<String> strings);

    List<Product> findFirst2ByOrderByNameAsc();

    List<Product> findTop2ByOrderByPriceDesc();
}
