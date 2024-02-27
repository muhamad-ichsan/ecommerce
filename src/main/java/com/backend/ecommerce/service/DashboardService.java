package com.backend.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.ecommerce.dto.DashboardDto;
import com.backend.ecommerce.repository.TransactionRepository;

@Service
public class DashboardService {

	@Autowired
	private TransactionRepository transactionRepository;

	public DashboardDto getDashboardByDate(String dateStart, String dateEnd) {
		List<Object[]> result = transactionRepository.findByDate(dateStart, dateEnd);
		return mapToDashboardDto(result);
	}

	public DashboardDto getDashboardByProduct(String product) {
		List<Object[]> result = transactionRepository.findByProduct(product);
		return mapToDashboardDto(result);
	}

	public DashboardDto getDashboardByCategory(String category) {
		List<Object[]> result = transactionRepository.findByCategory(category);
		return mapToDashboardDto(result);
	}
	
	private DashboardDto mapToDashboardDto(List<Object[]> result) {
		if (!result.isEmpty()) {
			Object[] row = result.get(0);
			Integer qty = ((Number) row[0]).intValue();
			BigDecimal amount = (BigDecimal) row[1];
			
			DashboardDto dashboardDto = new DashboardDto();
			dashboardDto.setQty(qty);
			dashboardDto.setAmount(amount);
			
			return dashboardDto;
		} else {
			return null;
		}
	}
}
