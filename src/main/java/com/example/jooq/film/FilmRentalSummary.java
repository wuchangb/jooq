package com.example.jooq.film;

import java.math.BigDecimal;

public record FilmRentalSummary(
	Long filmId,
	String title,
	BigDecimal averageRentalDuration
) {
}
