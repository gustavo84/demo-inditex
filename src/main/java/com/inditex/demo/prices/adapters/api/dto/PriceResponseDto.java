package com.inditex.demo.prices.adapters.api.dto;

import java.time.LocalDateTime;
import com.inditex.demo.prices.domain.model.Price;

/**
 * DTO de respuesta que representa el precio preferente de un producto.
 *
 * @param productId identificador del producto
 * @param brandId   identificador de la marca
 * @param priceList identificador de la tarifa aplicada
 * @param startDate fecha de inicio de validez
 * @param endDate   fecha de fin de validez
 * @param price     precio final aplicado
 */
public record PriceResponseDto(
        Integer productId,
        Integer brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double price
) {

    /**
     * Constructor que crea el DTO a partir del modelo de dominio {@link Price}.
     *
     * @param price objeto de dominio Price
     */
    public PriceResponseDto(final Price price) {
        this(
            price.getProductId(),
            price.getBrandId(),
            price.getPriceList(),
            price.getStartDate(),
            price.getEndDate(),
            price.getPrice()
        );
    }
}
