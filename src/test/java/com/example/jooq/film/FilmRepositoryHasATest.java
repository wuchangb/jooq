package com.example.jooq.film;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.tables.pojos.Film;

@SpringBootTest
class FilmRepositoryHasATest {

	@Autowired
	FilmRepositoryHasA filmRepositoryHasA;
	@Autowired
	FilmRepositoryIsA filmRepositoryIsA;

	@Test
	void test() {
		var start = 100;
		var end = 180;

		List<Film> films = filmRepositoryHasA.findByRangeBetween(start, end);
		assertThat(films)
			.allSatisfy(film -> assertThat(film.getLength()).isBetween(start, end));
	}

	@Test
	void test_2() {
		var start = 100;
		var end = 180;

		List<Film> films = filmRepositoryIsA.fetchRangeOfJLength(start, end);
		assertThat(films)
			.allSatisfy(film -> assertThat(film.getLength()).isBetween(start, end));
	}

}