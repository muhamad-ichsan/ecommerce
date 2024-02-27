package com.backend.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8085990018133999425L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long id;
	
	@NotNull(message = "Date is required")
	@Column(name = "transaction_date")
	private Date date;

	@Column(name = "transaction_amount")
	private BigDecimal amount;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Long id, Date date, BigDecimal amount) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
	}
	
	public Transaction(Date date, BigDecimal amount) {
		super();
		this.date = date;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
