package com.inditex.demo;

import com.inditex.demo.prices.adapters.api.PriceFacade;
import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;
import com.inditex.demo.prices.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PriceFacade priceFacade;

    @BeforeEach
    void setUp() {
    }

    private PriceResponseDto createDto(Double price, String date) {
        Price p = new Price();
        p.setProductId(35455);
        p.setBrandId(1);
        p.setPriceList(1);
        p.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        p.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        p.setPrice(price);
        return new PriceResponseDto(p);
    }

    private void testCase(Double price, String date) {
        PriceResponseDto dto = createDto(price, date);

        when(priceFacade.getPreferredPrice(any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(Mono.just(dto));

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

    @Test
    void test1() {
        testCase(35.50, "2020-06-14T10:00:00");
    }

    @Test
    void test2() {
        testCase(25.45, "2020-06-14T16:00:00");
    }

    @Test
    void test3() {
        testCase(35.50, "2020-06-14T21:00:00");
    }

    @Test
    void test4() {
        testCase(30.50, "2020-06-15T10:00:00");
    }

    @Test
    void test5() {
        testCase(38.95, "2020-06-16T21:00:00");
    }
}
