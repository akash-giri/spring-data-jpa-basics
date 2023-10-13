package com.springdatajpa.springboot.repository;

import com.springdatajpa.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
     * Problem with Query Methods
     * ------------------------------------------------------------------------------------------
     * 1.Keyword support - if the method name parse doesn't support the required keyword,
     *                     we cannot use this strategy.
     * 2.The method names of complex query methods are long and ugly.
     *     List<Product> findByDescriptionContainsOrNameContainsAllIgnoreCase(String description,String name);
     * 3. And this is for just two parameters. what happens when you want to create a query for
     *     5 parameters?
     * 4. This is the point when you'll most likely want to prefer to write your own queries.
     *      This is doable via the @Query annotation.
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

    /**
     * Understanding @Query annotation
     * 1. we can configure the invoked database query by annotating the query method with the
     *   query annotation
     *
     * Define JPQL query with index parameters
     * @Query("select p from Product p where p.name =?1 or p.description=?2")
     * Product findByNameOrDescriptionJPQLIndexParam(String name,String description);
     *
     * 2.No need to follow query method naming Conventions.
     * 3.We can use the @Query annotation in spring Data JPA to execute both JPQL and native SQL queries.
     *
     * ---------------------------------------------------------------------------------------------------
     * JPQL Query with Index(Position) parameters
     * 1. when using position-based parameters, you have to keep track of the order in which you supply
     *    the parameters in:
     *
     *  public interface ProductRepository extends JpaRepository<Product, Long>{
     *      // Define the JPQL query with index parameters
     * @query ("select p from Product p where p.name =?1 or p.description =?2")
     *   Product findByNameOrDescriptionJPQLIndexParam(String name, String description);
     *  }
     *
     * 2. The first parameter passed to the method is mapped to ?1, the second is mapped to ?2, etc. If
     *   you accidentally switch these up - your query will likely throw an exception, or silently produce
     *   wrong results.
     *
     */
    // Define JPQL query using @Query annotation with index or position parameters
    @Query("SELECT p from Product p where p.name = ?1 or p.description = ?2")
    Product findByNameOrDescriptionJPQLIndexParam(String name, String description);

    // Define JPQL query using @Query annotation with Named parameters
    @Query("SELECT p from Product p where p.name = :name or p.description = :description")
    Product findByNameOrDescriptionJPQLNamedParam(@Param("name") String name,
                                                  @Param("description") String description);

    // Define Native SQL query using @Query annotation with index or position parameters
    @Query(value = "select * from products p where p.name = ?1 or p.description = ?2", nativeQuery = true)
    Product findByNameOrDescriptionSQLIndexParam(String name, String description);

    // Define Native SQL query using @Query annotation with Named parameters
    @Query(value = "select * from products p where p.name = :name or p.description = :description"
            ,nativeQuery = true)
    Product findByNameOrDescriptionSQLNamedParam(@Param("name") String name,
                                                 @Param("description") String description);

    /**
     * Steps to Define Named JPQL Query
     * if we want to create a JPQL query, we  must follow these steps:
     * 1. Annotate the entity with the @NamedQuery annotation from jpa
     * 2. use @NamedQuery annotation's name attribute to set name of the named query (Product.findBySku)
     * 3. use @NamedQuery annotation's query attribute to set the JPQL query (SELECT p from
     *    Product p where p.sku=:sku) as the value
     *
     * @Entity
     * @NamedQuery(name="Product.findBySku",
     *             query = "SELECT p from Product p WHERE p.sku=:sku"
     *             )
     * public class Product{
     *
     * }
     * 4. use named query name in a repository
     *    // named query method
     *    Product findBySku(@Param("sku")String sku);
     */

    // Define Named JPQL query
    Product findByPrice(@Param("price") BigDecimal price);

    List<Product> findAllOrderByNameDesc();

    /**
     * Define Multiple Named JPQL Queries
     * if we want to create a multipleJPQL query, we must follow these steps:
     * 1. Annotate the entity with the @NamedQueries annotation from JPA/Hibernate.
     * 2. Use Multiple @NamedQuery annotations from JPA/Hibernate to define a named queries
     * 3. Set the name of the named query as the value of the @NamedQuery annotation's name
     *    attribute.
     * 4. Set the JPQL query as the value of the @NamedQuery annotation's query attribute.
     *
     * @NamedQueries(
     *        {
     * -        @NamedQuery(name="Product.findAllOrderByNameDesc",
     *                      query ="select p from Product p ORDER BY p.name DESC"
     *          ),
     * -        @NamedQuery(name="Product.findByPrice",
     *                      query ="select p from Product p WHERE p.price =:price"
     *          )
     *        }
     * )
     */

    // Define Named native SQL query
    @Query(nativeQuery = true)
    Product findByDescription(@Param("description") String description);

    List<Product> findAllOrderByNameASC();
}

