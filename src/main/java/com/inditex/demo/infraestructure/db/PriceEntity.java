package com.inditex.demo.infraestructure.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad que representa un registro de precios en la base de datos.
 */
@Getter
@Setter
@Table("PRICES")
public final class PriceEntity {

    /** Identificador de la lista de precios. */
    @Id
    @Column("PRICE_LIST")
    private Integer priceList;

    /** Identificador del producto. */
    @Column("PRODUCT_ID")
    private Integer productId;

    /** Identificador de la marca. */
    @Column("BRAND_ID")
    private Integer brandId;

    /** Prioridad del precio. */
    @Column("PRIORITY")
    private Integer priority;

    /** Moneda asociada al precio. */
    @Column("CURR")
    private String currency;

    /** Valor del precio. */
    @Column("PRICE")
    private Double price;

    /** Fecha de inicio de validez del precio. */
    @Column("START_DATE")
    private LocalDateTime startDate;

    /** Fecha de fin de validez del precio. */
    @Column("END_DATE")
    private LocalDateTime endDate;
}
