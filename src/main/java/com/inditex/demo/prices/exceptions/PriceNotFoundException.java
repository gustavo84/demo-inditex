package com.inditex.demo.prices.exceptions;

import java.time.LocalDateTime;

/**
 * Excepción lanzada cuando no se encuentra un precio válido
 * para un producto, marca y fecha determinados.
 */
public final class PriceNotFoundException extends RuntimeException {

    /** Identificador de serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepción.
     *
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @param applyDate fecha de aplicación
     */
    public PriceNotFoundException(
            final Integer productId,
            final Integer brandId,
            final LocalDateTime applyDate
    ) {
        super(
            "No price found for product " + productId
            + ", brand " + brandId
            + " at " + applyDate
        );
    }
}
