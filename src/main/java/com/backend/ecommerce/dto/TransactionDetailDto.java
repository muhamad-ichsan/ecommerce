package com.backend.ecommerce.dto;

import javax.validation.constraints.NotNull;

import com.backend.ecommerce.model.Product;

public class TransactionDetailDto {
	@NotNull(message = "Product is required")
	private Product product;
	
	@NotNull(message = "Quantity is required")
	private Integer qty;
	
	public TransactionDetailDto() {
		
	}
	
	public TransactionDetailDto(Product product, Integer qty) {
		super();
		this.product = product;
		this.qty = qty;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	
}
