package com.backend.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.backend.ecommerce.model.TransactionDetail;

public interface TransactionDetailRepository extends PagingAndSortingRepository<TransactionDetail, Long> {
	
	@Query("SELECT td FROM TransactionDetail td WHERE td.transaction.id = :id")
	List<TransactionDetail> findByTransactionId(@Param("id") Long id);
}
