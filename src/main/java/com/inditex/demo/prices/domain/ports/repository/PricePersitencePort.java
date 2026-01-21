package com.inditex.demo.prices.domain.ports.repository;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import com.inditex.demo.prices.domain.model.Price;

/**
 * Puerto del dominio para la obtención de precios.
 */
public interface PricePersitencePort {

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return precio preferente envuelto en un {@link Mono}
     */
    Mono<Price> getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId
    );
}
