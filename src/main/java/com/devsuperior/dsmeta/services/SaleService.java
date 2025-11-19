package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;


    public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


    public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable){
        LocalDate maxDateConverted;
        LocalDate minDateConverted;
        if (maxDate.isEmpty()) {
           maxDateConverted = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }
        else{
            maxDateConverted = LocalDate.parse(maxDate);
        }
        if (minDate.isEmpty()) {
           minDateConverted = maxDateConverted.minusYears(1L);
        }
        else{
            minDateConverted = LocalDate.parse(minDate);
        }
        Page<Sale> resultPage = repository.searchReport(
                minDateConverted,
                maxDateConverted,
                name,
                pageable
        );

        return resultPage.map(SaleReportDTO::new);
    }
}
