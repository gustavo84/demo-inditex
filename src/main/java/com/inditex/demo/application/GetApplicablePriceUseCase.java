package com.inditex.demo.application;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import com.inditex.demo.prices.domain.model.Price;

/**
 * Servicio para obtener el precio preferente.
 */
public interface GetApplicablePriceUseCase  {

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return Mono con el precio preferente
     */
    Mono<Price> getPreferredPrice(
            LocalDateTime applyDate,
            Integer productId,
            Integer brandId
    );
}
