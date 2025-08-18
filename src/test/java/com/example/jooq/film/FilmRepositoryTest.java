package com.example.jooq.film;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.tables.pojos.Film;
import com.example.jooq.web.FilmWithActorPagedResponse;

@SpringBootTest
class FilmRepositoryTest {

	@Autowired
	FilmRepository filmRepository;
	@Autowired
	FilmService filmService;

	@Test
	void test() {
		Film film = filmRepository.findById(1L);
		assertThat(film).isNotNull();
	}

	@Test
	void test_2() {
		SimpleFilmInfo simpleFilmInfo = filmRepository.findSimpleFilmInfoById(1L);
		assertThat(simpleFilmInfo).hasNoNullFieldsOrProperties();
	}

	@Test
	void test3() {
		FilmWithActorPagedResponse filmActorPageResponse = filmService.getFilmActorPageResponse(1L, 20L);
		assertThat(filmActorPageResponse.filmActors()).hasSize(20);
	}
}