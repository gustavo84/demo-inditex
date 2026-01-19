package com.inditex.demo.prices.adapters.pricedb;

import com.inditex.demo.prices.domain.model.Price;

import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface PriceJPARepository extends ReactiveCrudRepository<Price, Integer> {

    Mono<Price> findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
            Integer productId, Integer brandId, LocalDateTime apply, LocalDateTime app2
                                                                                       );
}
