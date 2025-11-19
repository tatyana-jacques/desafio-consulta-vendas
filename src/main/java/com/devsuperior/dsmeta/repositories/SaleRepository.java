package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(" SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj)" +
            "       FROM Sale obj" +
            "       JOIN obj.seller seller" +
            "       WHERE obj.date BETWEEN :min AND :max" +
            "       AND LOWER(seller.name) LIKE LOWER(CONCAT('%', :name, '%'))" )
    Page<SaleReportDTO> searchReport(LocalDate min, LocalDate max, String name, Pageable pageable);

}
