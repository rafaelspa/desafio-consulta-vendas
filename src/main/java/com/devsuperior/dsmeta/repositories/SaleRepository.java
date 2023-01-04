package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDto;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj) FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate AND " +
            "UPPER(obj.seller.name) LIKE(CONCAT('%',UPPER(:name),'%'))")
    Page<SaleMinDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDto(obj.seller.name, SUM(obj.amount)) FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller " +
            "ORDER BY obj.seller.name ASC")
    Page<SaleSummaryDto> searchSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
