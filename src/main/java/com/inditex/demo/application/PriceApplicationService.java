package com.inditex.demo.application;

import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PricePersitencePort;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación para obtener el precio preferente.
 */
@Service
public final class PriceApplicationService implements GetApplicablePriceUseCase {

    /** Repositorio de precios. */
    private final PricePersitencePort pricePersistencePort;

    /**
     * Constructor del servicio.
     *
     * @param pricePersistencePort repositorio de precios
     */
    public PriceApplicationService(final PricePersitencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId id del producto
     * @param brandId id de la marca
     * @return Price
     */
    @Override
    public Price getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId) {

        return 
        pricePersistencePort.getPreferredPrice(applyDate, productId, brandId);

    }
}
