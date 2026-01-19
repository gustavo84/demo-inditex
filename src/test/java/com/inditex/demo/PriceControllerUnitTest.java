package com.inditex.demo;
import com.inditex.demo.prices.adapters.api.PriceController;
import com.inditex.demo.prices.adapters.api.PriceFacade;
import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;
import com.inditex.demo.prices.domain.model.Price;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;


@WebFluxTest(PriceController.class)
class PriceControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PriceFacade priceFacade;

    @Test
    void testGetPreferredPrice() {
    	Price price = new Price();
    	price.setProductId(35455);
    	price.setBrandId(1);
    	price.setPriceList(1);
    	price.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
    	price.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
    	price.setPrice(35.50);

    	PriceResponseDto dto = new PriceResponseDto(price);
        when(priceFacade.getPreferredPrice(any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(Mono.just(dto));

        // Llamada al endpoint
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("applyDate", "2020-06-14T10:00:00")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(35.50);

        // Verificación de que se llamó al facade
        verify(priceFacade, times(1))
                .getPreferredPrice(any(LocalDateTime.class), anyInt(), anyInt());
    }
}
