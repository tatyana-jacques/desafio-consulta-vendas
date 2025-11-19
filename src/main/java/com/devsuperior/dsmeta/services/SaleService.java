package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
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
        LocalDate maxDateConverted = maxDateConversion(maxDate);
        LocalDate minDateConverted = minDateConversion(minDate, maxDateConverted);

        Page<Sale> resultPage = repository.searchReport(
                minDateConverted,
                maxDateConverted,
                name,
                pageable
        );

        return resultPage.map(SaleReportDTO::new);
    }

    public Page<SellerMinDTO> getSummary(String minDate, String maxDate, Pageable pageable){
        LocalDate maxDateConverted = maxDateConversion(maxDate);
        LocalDate minDateConverted = minDateConversion(minDate, maxDateConverted);

        return repository.searchSummary(
                minDateConverted,
                maxDateConverted,
                pageable
        );
    }


    private LocalDate maxDateConversion(String date){
        if (date.isEmpty()) {
            return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        }
            return LocalDate.parse(date);

    }

    private LocalDate minDateConversion(String date, LocalDate maxDate){
        if (date.isEmpty()) {
            return maxDate.minusYears(1L);
        }
        return LocalDate.parse(date);


    }


}
