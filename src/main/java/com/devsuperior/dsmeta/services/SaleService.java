package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDto;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) throws ParseException {
		LocalDate searchMinDate;
		LocalDate searchMaxDate;
		if (Objects.equals(maxDate, "")){
			searchMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			searchMaxDate = LocalDate.parse(maxDate, dateTimeFormatter);
		}

		if (Objects.equals(minDate, "")){
			searchMinDate = searchMaxDate.minusYears(1L);
		} else {
			searchMinDate = LocalDate.parse(minDate, dateTimeFormatter);
		}

		return repository.searchReport(searchMinDate, searchMaxDate, name, pageable);
	}

	public Page<SaleSummaryDto> searchSummary(String minDate, String maxDate, Pageable pageable) {
		LocalDate searchMinDate;
		LocalDate searchMaxDate;
		if (Objects.equals(maxDate, "")){
			searchMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			searchMaxDate = LocalDate.parse(maxDate, dateTimeFormatter);
		}
		if (Objects.equals(minDate, "")){
			searchMinDate = searchMaxDate.minusYears(1L);
		} else {
			searchMinDate = LocalDate.parse(minDate, dateTimeFormatter);
		}

		return repository.searchSummary(searchMinDate, searchMaxDate, pageable);
	}
}
