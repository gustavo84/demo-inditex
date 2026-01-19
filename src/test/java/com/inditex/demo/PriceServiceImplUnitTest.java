package com.inditex.demo;

import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PriceRepository;
import com.inditex.demo.prices.domain.ports.service.PriceServiceImpl;
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
    private PriceServiceImpl priceService;

    public PriceServiceImplUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPreferredPriceFound() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        Price price = new Price();
        price.setProductId(35455);
        price.setBrandId(1);
        price.setPrice(35.50);

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
