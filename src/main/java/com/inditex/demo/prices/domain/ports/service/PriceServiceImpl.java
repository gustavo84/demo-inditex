package com.inditex.demo.prices.domain.ports.service;

import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PriceRepository;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;


    
    @Override
    public Mono<Price> getPreferredPrice(
            LocalDateTime applyDate,
            Integer productId,
            Integer brandId
    ) {
        return priceRepository.getPreferredPrice(applyDate, productId, brandId)
                .switchIfEmpty(
                    Mono.error(new PriceNotFoundException(productId, brandId, applyDate))
                );
    }



}
