package com.backend.ecommerce.dto;

import java.math.BigDecimal;

public class DashboardDto {
	
	private Integer	qty;
	private BigDecimal amount;
	
	
	
	public DashboardDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DashboardDto(Integer qty, BigDecimal amount) {
		super();
		this.qty = qty;
		this.amount = amount;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
