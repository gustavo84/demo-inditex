package com.inditex.demo.prices.domain.ports.service;

import com.inditex.demo.prices.domain.model.Price;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceService {

    Mono<Price> getPreferredPrice(LocalDateTime applyDate, Integer productId, Integer brandId);
}
