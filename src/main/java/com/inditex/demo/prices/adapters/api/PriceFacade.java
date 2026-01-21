package com.inditex.demo.prices.adapters.api;

import java.time.LocalDateTime;

import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;

import reactor.core.publisher.Mono;

/**
 * Fachada para la obtención del precio preferente.
 */
public interface PriceFacade {

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return respuesta con el precio preferente
     */
    Mono<PriceResponseDto> getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId
    );
}
