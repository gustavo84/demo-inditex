package com.inditex.demo;

import com.inditex.demo.application.PriceApplicationService;
import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PriceRepository;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplUnitTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceApplicationService priceService;

    public PriceServiceImplUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPreferredPriceFound() {
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

        when(priceRepository.getPreferredPrice(date, 35455, 1))
                .thenReturn(Mono.just(price));

        Price result = priceService.getPreferredPrice(date, 35455, 1).block();

        assertNotNull(result);
        assertEquals(35.50, result.getPrice());

        verify(priceRepository, times(1))
                .getPreferredPrice(date, 35455, 1);
    }

    @Test
    void testGetPreferredPriceNotFound() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        when(priceRepository.getPreferredPrice(date, 35455, 1))
                .thenReturn(Mono.empty());

        assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getPreferredPrice(date, 35455, 1).block()
        );

        verify(priceRepository, times(1))
                .getPreferredPrice(date, 35455, 1);
    }
}
