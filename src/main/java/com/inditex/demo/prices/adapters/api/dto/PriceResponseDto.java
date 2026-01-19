package com.inditex.demo.prices.adapters.api.dto;

import com.inditex.demo.prices.domain.model.Price;

import java.time.LocalDateTime;

public record PriceResponseDto(Integer productId, Integer brandId, Integer priceList, LocalDateTime startDate,
                            LocalDateTime endDate, Double price) {

    public PriceResponseDto(Price price) {

        this(price.getProductId(), price.getBrandId(), price.getPriceList(), price.getStartDate(), price.getEndDate(),
                price.getPrice());
    }
}
