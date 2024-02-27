package com.backend.ecommerce.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.backend.ecommerce.model.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}
