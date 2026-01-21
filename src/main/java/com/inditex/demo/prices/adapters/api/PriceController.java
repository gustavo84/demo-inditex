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

/**
 * Controlador REST para la consulta del precio preferente.
 */
@RestController
@RequestMapping("/prices")
@Validated
public class PriceController {

    /** Fachada del servicio de precios. */
    private final PriceFacade priceFacade;

    /**
     * Constructor del controlador.
     *
     * @param priceFacade fachada del servicio de precios
     */
    public PriceController(final PriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    /**
     * Obtiene el precio preferente de un producto según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return precio preferente envuelto en un {@link Mono}
     */
    @Operation(summary = "Obtener precio preferido de un producto")
    @GetMapping
    public Mono<PriceResponseDto> getPreferredPrice(
            @RequestParam("applyDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @NotNull final LocalDateTime applyDate,

            @RequestParam("productId")
            @NotNull final Integer productId,

            @RequestParam("brandId")
            @NotNull final Integer brandId
    ) {
        return priceFacade.getPreferredPrice(applyDate, productId, brandId);
    }
}
