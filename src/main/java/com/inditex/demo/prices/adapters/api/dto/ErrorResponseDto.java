package com.inditex.demo.prices.adapters.api.dto;

/**
 * DTO que representa una respuesta de error estándar.
 */
public final class ErrorResponseDto {

    /** Código del error. */
    private String code;

    /** Mensaje descriptivo del error. */
    private String message;

    /**
     * Constructor del DTO.
     *
     * @param code    código del error
     * @param message mensaje descriptivo del error
     */
    public ErrorResponseDto(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Obtiene el código del error.
     *
     * @return código del error
     */
    public String getCode() {
        return code;
    }

    /**
     * Obtiene el mensaje del error.
     *
     * @return mensaje del error
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el código del error.
     *
     * @param code nuevo código
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Establece el mensaje del error.
     *
     * @param message nuevo mensaje
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
