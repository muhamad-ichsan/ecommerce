package com.backend.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetail implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7259269485633106254L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "td_id")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

	@NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

	@NotNull(message = "Quantity is required")
	@Column(name = "td_qty")
	private Integer qty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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
