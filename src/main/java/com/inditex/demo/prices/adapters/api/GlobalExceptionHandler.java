package com.inditex.demo.prices.adapters.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.inditex.demo.prices.adapters.api.dto.ErrorResponseDto;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import reactor.core.publisher.Mono;

/**
 * Manejador global de excepciones para la API de precios.
 */
@RestControllerAdvice
public final class GlobalExceptionHandler {

    /**
     * Maneja la excepción {@link PriceNotFoundException}.
     *
     * @param ex excepción lanzada cuando no se encuentra un precio
     * @return respuesta de error con código PRICE_NOT_FOUND
     */
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponseDto> handlePriceNotFound(final PriceNotFoundException ex) {
        return Mono.just(
                new ErrorResponseDto("PRICE_NOT_FOUND", ex.getMessage())
        );
    }

    /**
     * Maneja errores de validación de parámetros.
     *
     * @param ex excepción de validación
     * @return respuesta de error con código VALIDATION_ERROR
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponseDto> handleValidationErrors(final WebExchangeBindException ex) {
        return Mono.just(
                new ErrorResponseDto("VALIDATION_ERROR", "Parámetros inválidos")
        );
    }
}
