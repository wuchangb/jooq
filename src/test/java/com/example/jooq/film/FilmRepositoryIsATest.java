package com.example.jooq.film;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.tables.pojos.Film;

@SpringBootTest
class FilmRepositoryIsATest {

	@Autowired
	FilmRepositoryIsA filmRepositoryIsA;

	@Test
	void test() {
		Film film = filmRepositoryIsA.findById(1L);
	}
}