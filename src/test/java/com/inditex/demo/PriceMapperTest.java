package com.inditex.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.inditex.demo.infraestructure.db.PriceEntity;
import com.inditex.demo.infraestructure.db.mapper.PriceMapper;
import com.inditex.demo.prices.domain.model.Price;

class PriceMapperTest {

    private final PriceMapper mapper = new PriceMapper();

    @Test
    void testToDomain() {
        PriceEntity entity = new PriceEntity();
        entity.setPriceList(1);
        entity.setProductId(35455);
        entity.setBrandId(1);
        entity.setPriority(0);
        entity.setCurrency("EUR");
        entity.setPrice(35.50);
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));

        Price price = mapper.toDomain(entity);

        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(0, price.getPriority());
        assertEquals("EUR", price.getCurrency());
        assertEquals(35.50, price.getPrice());
    }
}
