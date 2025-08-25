package com.example.jooq.config;

import org.jooq.impl.EnumConverter;

import com.example.jooq.film.FilmPriceSummary;
import com.example.jooq.film.FilmPriceSummary.PriceCategory;

public class PriceCategoryConverter extends EnumConverter<String, PriceCategory> {
	public PriceCategoryConverter() {
		super(String.class, FilmPriceSummary.PriceCategory.class, FilmPriceSummary.PriceCategory::getCode);
	}
}
