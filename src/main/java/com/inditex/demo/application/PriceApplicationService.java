package com.inditex.demo.application;

import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PriceRepository;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación para obtener el precio preferente.
 */
@Service
public final class PriceApplicationService implements PriceService {

    /** Repositorio de precios. */
    private final PriceRepository priceRepository;

    /**
     * Constructor del servicio.
     *
     * @param priceRepository repositorio de precios
     */
    public PriceApplicationService(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId id del producto
     * @param brandId id de la marca
     * @return Mono con el precio preferente o error si no existe
     */
    @Override
    public Mono<Price> getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId) {

        return priceRepository
                .getPreferredPrice(applyDate, productId, brandId)
                .switchIfEmpty(
                        Mono.error(new PriceNotFoundException(productId, brandId, applyDate))
                );
    }
}
