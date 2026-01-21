package com.inditex.demo.prices.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un precio aplicable a un producto.
 */
@Getter
@RequiredArgsConstructor
public final class Price {

    /** Identificador de la tarifa. */
    private final Integer priceList;

    /** Identificador del producto. */
    private final Integer productId;

    /** Identificador de la marca. */
    private final Integer brandId;

    /** Prioridad del precio. */
    private final Integer priority;

    /** Moneda del precio. */
    private final String currency;

    /** Valor del precio. */
    private final Double price;

    /** Fecha de inicio de validez. */
    private final LocalDateTime startDate;

    /** Fecha de fin de validez. */
    private final LocalDateTime endDate;
}
