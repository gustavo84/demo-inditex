package com.inditex.demo.prices.adapters.api;

import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;
import com.inditex.demo.prices.domain.ports.service.PriceService;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PriceFacadeImpl implements PriceFacade {

    private final PriceService priceService;


    public PriceFacadeImpl(PriceService priceService) {
        this.priceService = priceService;
    }


    public Mono<PriceResponseDto> getPreferredPrice(
            LocalDateTime applyDate,
            Integer productId,
            Integer brandId
    ) {
        return priceService.getPreferredPrice(applyDate, productId, brandId)
                .map(PriceResponseDto::new);
    }

}
