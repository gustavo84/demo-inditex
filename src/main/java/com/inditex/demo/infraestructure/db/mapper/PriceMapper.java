package com.inditex.demo.infraestructure.db.mapper;

import org.springframework.stereotype.Component;

import com.inditex.demo.infraestructure.db.PriceEntity;
import com.inditex.demo.prices.domain.model.Price;

/**
 * Mapper encargado de convertir entidades de base de datos
 * en objetos de dominio.
 */
@Component
public final class PriceMapper {

    /**
     * Convierte una entidad {@link PriceEntity} en un modelo de dominio {@link Price}.
     *
     * @param entity entidad de base de datos
     * @return objeto de dominio Price
     */
    public Price toDomain(final PriceEntity entity) {
        return new Price(
                entity.getPriceList(),
                entity.getProductId(),
                entity.getBrandId(),
                entity.getPriority(),
                entity.getCurrency(),
                entity.getPrice(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }
}
