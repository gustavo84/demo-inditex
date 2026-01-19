package com.inditex.demo.prices.adapters.api;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.demo.prices.adapters.api.dto.PriceResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/prices")
@Validated
public class PriceController {

    private final PriceFacade priceFacade;

    public PriceController(PriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    @Operation(summary = "Obtener precio preferido de un producto")
    @GetMapping
    public Mono<PriceResponseDto> getPreferredPrice(
            @RequestParam("applyDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @NotNull LocalDateTime applyDate,

            @RequestParam("productId")
            @NotNull Integer productId,

            @RequestParam("brandId")
            @NotNull Integer brandId
    ) {
        return priceFacade.getPreferredPrice(applyDate, productId, brandId);
    }
}
