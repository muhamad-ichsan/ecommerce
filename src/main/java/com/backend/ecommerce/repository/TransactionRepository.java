package com.backend.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.backend.ecommerce.model.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

	@Query(value = "Select SUM(td.td_qty) as qty, " 
				+ "SUM(td.td_qty * p.product_price) as amount "
				+ "FROM transaction t " 
				+ "JOIN transaction_detail td ON td.transaction_id = t.transaction_id "
				+ "JOIN product p ON p.product_id = td.product_id "
				+ "JOIN category c ON c.category_id = p.category_category_id "
				+ "WHERE t.transaction_date between :dateStart and :dateEnd", nativeQuery = true)
	List<Object[]> findByDate(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);

	@Query(value = "SELECT SUM(td.td_qty) as qty, "
            	+ "SUM(td.td_qty * p.product_price) as amount "
            	+ "FROM transaction t "
            	+ "JOIN transaction_detail td ON td.transaction_id = t.transaction_id "
            	+ "JOIN product p ON p.product_id = td.product_id "
            	+ "WHERE p.product_name LIKE %:product%", nativeQuery = true)
	List<Object[]> findByProduct(@Param("product") String product);

	@Query(value = "SELECT SUM(td.td_qty) as qty, "
            	+ "SUM(td.td_qty * p.product_price) as amount "
            	+ "FROM transaction t "
            	+ "JOIN transaction_detail td ON td.transaction_id = t.transaction_id "
            	+ "JOIN product p ON p.product_id = td.product_id "
            	+ "JOIN category c ON c.category_id = p.category_category_id "
            	+ "WHERE c.category_name LIKE %:category%", nativeQuery = true)
	List<Object[]> findByCategory(@Param("category") String category);


}
