package com.example.jooq.film;

public record SimpleFilmInfo(
	Long filmId,
	String title,
	String description
) {
}
