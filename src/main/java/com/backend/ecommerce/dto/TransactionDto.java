package com.backend.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.backend.ecommerce.model.TransactionDetail;

public class TransactionDto {
	
	private Long id;
	
	@NotNull(message = "Date is required")
	private String date;

	@NotNull(message = "Transaction Detail is required")
	private List<TransactionDetail> detail;
	
	private BigDecimal amount;
	
	public TransactionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionDto(Long id, String date, List<TransactionDetail> detail, BigDecimal amount) {
		super();
		this.id = id;
		this.date = date;
		this.detail = detail;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TransactionDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<TransactionDetail> detail) {
		this.detail = detail;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
