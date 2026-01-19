package com.inditex.demo.prices.domain.ports.repository;

import com.inditex.demo.prices.domain.model.Price;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


public interface PriceRepository {

    Mono<Price> getPreferredPrice(LocalDateTime applyDate, Integer productId, Integer brandId);
}
