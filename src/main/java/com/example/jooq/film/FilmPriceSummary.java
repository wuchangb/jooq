package com.example.jooq.film;

import java.math.BigDecimal;

import lombok.Getter;

public record FilmPriceSummary(
	Long filmId,
	String title,
	BigDecimal rentalRate,
	PriceCategory priceCategory,
	Long totalInventory
) {

	@Getter
	public enum PriceCategory {
		CHEAP("Cheap"),
		MODERATE("Moderate"),
		EXPENSIVE("Expensive");

		private final String code;

		PriceCategory(String code) {
			this.code = code;
		}

		public static PriceCategory findByCode(String code) {
			for (PriceCategory value : values()) {
				if (value.code.equals(code)) {
					return value;
				}
			}
			return null;
		}
	}
}
