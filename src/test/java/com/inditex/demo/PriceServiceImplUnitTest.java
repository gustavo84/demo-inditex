package com.inditex.demo;

import com.inditex.demo.application.PriceApplicationService;
import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PricePersitencePort;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplUnitTest {

    @Mock
    private PricePersitencePort priceRepository;

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
                .thenReturn(price);

        Price result = priceService.getPreferredPrice(date, 35455, 1);

        assertNotNull(result);
        assertEquals(35.50, result.getPrice());

        verify(priceRepository, times(1))
                .getPreferredPrice(date, 35455, 1);
    }

    @Test
    void testGetPreferredPriceNotFoundFor2019() {
        LocalDateTime date = LocalDateTime.parse("2019-06-14T10:00:00");

        // Configuramos el mock para que lance la excepción al pedir esa fecha
        when(priceRepository.getPreferredPrice(date, 35455, 1))
                .thenThrow(new PriceNotFoundException(35455, 1, date));

        // Comprobamos que el service lanza la excepción
        assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getPreferredPrice(date, 35455, 1)
        );


        // Verificamos que se llamó exactamente una vez al repositorio
        verify(priceRepository, times(1))
                .getPreferredPrice(date, 35455, 1);
    }


}
