package com.example.jooq;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.film.FilmPriceSummary;
import com.example.jooq.film.FilmRentalSummary;
import com.example.jooq.film.FilmRepositoryHasA;
import com.example.jooq.tables.pojos.Film;

@SpringBootTest
public class JooqSubqueryTest {

	@Autowired
	private FilmRepositoryHasA filmRepository;

	@Test
	@DisplayName("""
		영화별 대여료가
		1.0 이하면 'cheap'
		3.0 이하면 'moderate'
		그 이상이면 'expensive'로 분류하고,
		각 영화의 총 재고 수를 조회한다.
		""")
	void 스칼라_서브쿼리() {
		String filmTitle = "EGG";
		List<FilmPriceSummary> summaryList = filmRepository.findFilmPriceSummaryByFilmTitle(filmTitle);
	}

	@Test
	@DisplayName("평균 대여 기간이 가장 긴 영화부터 정렬해서 조회한다.")
	void from절_서브쿼리() {
		String filmTitle = "EGG";
		List<FilmRentalSummary> filmRentalSummaryList = filmRepository.findFilmRentalSummaryByFilmTitle(filmTitle);
	}

	@Test
	@DisplayName("대여된 기록이 있는영화가 있는 영화만 조회")
	void where절_서브쿼리() {
		String filmTitle = "EGG";
		List<Film> filmList = filmRepository.findRentedFilmByTitle(filmTitle);
	}
}
