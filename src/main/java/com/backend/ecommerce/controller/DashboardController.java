package com.backend.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ecommerce.dto.DashboardDto;
import com.backend.ecommerce.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	@GetMapping("/byDate")
	public DashboardDto getDashboardByDate(@RequestParam("dateStart") String dateStart, @RequestParam("dateEnd") String dateEnd) {
		return dashboardService.getDashboardByDate(dateStart, dateEnd);
	}
	
	@GetMapping("/byProduct")
	public DashboardDto getDashboardByProduct(@RequestParam("product") String product) {
		return dashboardService.getDashboardByProduct(product);
	}
	
	@GetMapping("/byCategory")
	public DashboardDto getDashboardByCategory(@RequestParam("category") String category) {
		return dashboardService.getDashboardByCategory(category);
	}
}
