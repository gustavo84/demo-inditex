package com.inditex.demo.prices.adapters.api;

import java.time.LocalDateTime;

import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;

import reactor.core.publisher.Mono;

public interface PriceFacade {

	Mono<PriceResponseDto> getPreferredPrice(LocalDateTime applyDate, Integer productId, Integer brandId);
}
