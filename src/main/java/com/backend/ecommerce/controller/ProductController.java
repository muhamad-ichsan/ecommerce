package com.backend.ecommerce.controller;

import com.backend.ecommerce.dto.ProductDto;
import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.model.Product;
import com.backend.ecommerce.service.CategoryService;
import com.backend.ecommerce.service.ProductService;
import com.backend.ecommerce.util.ResponseData;
import com.backend.ecommerce.util.ResponseHandler;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	private final ModelMapper modelMapper = new ModelMapper();

	@PostMapping
	public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody ProductDto productData, Errors errors) {
		return ResponseHandler.handleRequest(() -> {
			Product product = modelMapper.map(productData, Product.class);	
			return productService.save(product);
		}, errors);
	}

	@PutMapping
	public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody ProductDto productData, Errors errors) {
	    return ResponseHandler.handleRequest(() -> {
	        Optional<Product> opExistingProduct = Optional.ofNullable(productService.findById(productData.getId()));
	        if (opExistingProduct.isPresent()) {
	            Product existingProduct = opExistingProduct.get();
	            existingProduct.setName(productData.getName());
	            existingProduct.setDescription(productData.getDescription());
	            existingProduct.setPrice(productData.getPrice());
	            existingProduct.setStock(productData.getStock());

	            Optional<Category> categoryNew = Optional.ofNullable(categoryService.findById(productData.getCategory().getId()));
	            if (categoryNew.isPresent()) {
	                existingProduct.setCategory(categoryNew.get());
	                return productService.save(existingProduct);
	            } else {
	                throw new EntityNotFoundException("Category not found with ID: " + productData.getCategory().getId());
	            }
	        } else {
	            throw new EntityNotFoundException("Product not found with ID: " + productData.getId());
	        }
	    }, errors);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseData<String>> deleteById(@PathVariable("id") Long id) {
		return ResponseHandler.handleRequest(() -> {
			productService.deleteById(id);
			return "Product deleted successfully";
		});
	}
	
	@GetMapping
	public ResponseEntity<ResponseData<Iterable<Product>>> findAll() {
		return ResponseHandler.handleRequest(() -> productService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseData<Product>> findById(@PathVariable("id") Long id) {
		return ResponseHandler.handleRequest(() -> productService.findById(id));
	}
	
	@GetMapping("/filterByCategory")
	public ResponseEntity<ResponseData<List<Product>>> findProductByCategory(@RequestParam("category") String category) {
		return ResponseHandler.handleRequest(() -> productService.findByCategory(category));
	}
	
	@GetMapping("/filterByName")
	public ResponseEntity<ResponseData<List<Product>>> findProductByName(@RequestParam("name") String name) {
		return ResponseHandler.handleRequest(() -> productService.findByName(name));
	}
}
