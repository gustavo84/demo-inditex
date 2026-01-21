package com.inditex.demo;

import com.inditex.demo.application.PriceApplicationService;
import com.inditex.demo.prices.adapters.api.PriceFacadeImpl;
import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;

import com.inditex.demo.prices.domain.model.Price;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PriceFacadeImplUnitTest {

    @Mock
    private PriceApplicationService priceService;

    @InjectMocks
    private PriceFacadeImpl priceFacade;

    public PriceFacadeImplUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPreferredPrice() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        // Dominio puro
        Price price = new Price(
                1,                      // priceList
                35455,                  // productId
                1,                      // brandId
                0,                      // priority
                "EUR",                  // currency
                35.50,                  // price
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        when(priceService.getPreferredPrice(date, 35455, 1))
                .thenReturn(Mono.just(price));

        PriceResponseDto dto = priceFacade.getPreferredPrice(date, 35455, 1).block();

        assertNotNull(dto);
        assertEquals(35.50, dto.price());

        verify(priceService, times(1))
                .getPreferredPrice(date, 35455, 1);
    }
}
