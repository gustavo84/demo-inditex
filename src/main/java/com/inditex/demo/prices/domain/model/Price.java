package com.inditex.demo.prices.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("PRICES")
public class Price {

    @Id
    @Column("PRICE_LIST")
    private Integer priceList;

    @Column("PRODUCT_ID")
    private Integer productId;

    @Column("BRAND_ID")
    private Integer brandId;

    @Column("PRIORITY")
    private Integer priority;

    @Column("CURR")
    private String currency;   // ⚠️ ver punto 3

    @Column("PRICE")
    private Double price;

    @Column("START_DATE")
    private LocalDateTime startDate;

    @Column("END_DATE")
    private LocalDateTime endDate;
}
