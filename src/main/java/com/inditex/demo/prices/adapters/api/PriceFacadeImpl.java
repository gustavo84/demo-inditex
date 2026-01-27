package com.inditex.demo.prices.adapters.api;

import com.inditex.demo.application.PriceApplicationService;
import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Implementación de la fachada para obtener el precio preferente.
 */
@Component
public final class PriceFacadeImpl implements PriceFacade {

    /** Servicio de aplicación encargado de obtener precios. */
    private final PriceApplicationService priceService;

    /**
     * Constructor de la fachada.
     *
     * @param priceService servicio de aplicación de precios
     */
    public PriceFacadeImpl(final PriceApplicationService priceService) {
        this.priceService = priceService;
    }

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return Mono<PriceResponseDto> respuesta con el precio preferente
     */
    @Override
    public Mono<PriceResponseDto> getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId
    ) {
    	return Mono.fromCallable(() -> priceService.getPreferredPrice(applyDate, productId, brandId))
    			.subscribeOn(Schedulers.boundedElastic()) // decide threading
    			.map(PriceResponseDto::new);
    }
}
