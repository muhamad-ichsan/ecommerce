package com.backend.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product save(Product product) {
		try {
			return productRepository.save(product);
        } catch (Exception e) {
            throw new DuplicateKeyException("Duplicate entry for product: " + product.getName());
        }
	}

	public Product findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
	}

	public Iterable<Product> findAll() {
		Sort ascendingSort = Sort.by(Sort.Order.asc("id"));
		Iterable<Product> listProduct = productRepository.findAll(ascendingSort);
		List<Product> productList = new ArrayList<>();
		listProduct.forEach(productList::add);
		if (!productList.isEmpty()) {
			return productList;
		} else {
			throw new EntityNotFoundException("No products found");
		}
	}

	public void deleteById(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (Exception ex) {
			throw new EntityNotFoundException("Product with ID " + id + " not found");
		}
	}

	public List<Product> findByCategory(String category) {
	    List<Product> products = productRepository.findByCategory(category);
	    if (products.isEmpty()) {
	        throw new EntityNotFoundException("No products found for category: " + category);
	    }
	    return products;
	}

	public List<Product> findByName(String name) {
		List<Product> products = productRepository.findByName(name);
		if (products.isEmpty()) {
			throw new EntityNotFoundException("No products found with name: " + name);
		}
		return products;
	}

}
