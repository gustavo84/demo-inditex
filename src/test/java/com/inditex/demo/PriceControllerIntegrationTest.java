package com.inditex.demo;

import com.inditex.demo.infraestructure.db.PriceEntity;
import com.inditex.demo.prices.adapters.api.PriceFacade;
import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;
import com.inditex.demo.prices.domain.model.Price;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
@WebFluxTest
@AutoConfigureWebTestClient
class PriceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PriceFacade priceFacade;

    private PriceResponseDto createDto(Double price) {
        Price domainPrice = new Price(
                1,
                35455,
                1,
                0,
                "EUR",
                price,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
        );

        return new PriceResponseDto(domainPrice);
    }

    private void testCase(Double price, String date) {
        when(priceFacade.getPreferredPrice(any(), anyInt(), anyInt()))
                .thenReturn(Mono.just(createDto(price)));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("applyDate", date)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(price);
    }

    @Test void test1() { testCase(35.50, "2020-06-14T10:00:00"); }
    @Test void test2() { testCase(25.45, "2020-06-14T16:00:00"); }
    @Test void test3() { testCase(35.50, "2020-06-14T21:00:00"); }
    @Test void test4() { testCase(30.50, "2020-06-15T10:00:00"); }
    @Test void test5() { testCase(38.95, "2020-06-16T21:00:00"); }
}
