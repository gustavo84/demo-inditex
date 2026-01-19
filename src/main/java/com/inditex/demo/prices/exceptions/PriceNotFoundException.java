package com.inditex.demo.prices.exceptions;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PriceNotFoundException(Integer productId, Integer brandId, LocalDateTime applyDate) {
        super("No price found for product " + productId +
              ", brand " + brandId +
              " at " + applyDate);
    }
}
