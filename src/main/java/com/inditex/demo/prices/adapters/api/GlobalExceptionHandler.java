package com.inditex.demo.prices.adapters.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.inditex.demo.prices.adapters.api.dto.ErrorResponseDto;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponseDto> handlePriceNotFound(PriceNotFoundException ex) {
        return Mono.just(new ErrorResponseDto("PRICE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponseDto> handleValidationErrors(WebExchangeBindException ex) {
        return Mono.just(new ErrorResponseDto("VALIDATION_ERROR", "Parámetros inválidos"));
    }
}

