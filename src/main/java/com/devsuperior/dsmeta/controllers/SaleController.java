package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDto;
import com.devsuperior.dsmeta.services.SaleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

//	/sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson
	@GetMapping(value = "/report")
	public ResponseEntity<?> getReport(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(name = "name", defaultValue = "") String name,
			Pageable pageable) throws ParseException {
		Page<SaleMinDTO> dto = service.searchReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}
///sales/summary?minDate=2022-01-01&maxDate=2022-06-30
	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
			Pageable pageable) {

		Page<SaleSummaryDto> dto = service.searchSummary(minDate, maxDate, pageable);
		return ResponseEntity.ok(dto);
	}
}
