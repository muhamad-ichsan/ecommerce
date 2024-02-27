package com.backend.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.backend.ecommerce.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	@Query(value = "Select * FROM product p " 
					+ "WHERE p.product_name Like %:name% ", nativeQuery = true)
	List<Product> findByName(@Param("name") String name);
    
    @Query(value = "Select * FROM product p "
    				+ "JOIN category c ON c.category_id = p.category_category_id "
    				+ "WHERE c.category_name Like %:category% ", nativeQuery = true)
    List<Product> findByCategory(@Param("category") String category);
}
