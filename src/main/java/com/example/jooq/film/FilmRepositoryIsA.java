package com.example.jooq.film;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import com.example.jooq.tables.JActor;
import com.example.jooq.tables.JFilm;
import com.example.jooq.tables.JFilmActor;
import com.example.jooq.tables.daos.FilmDao;

//상속을 통해서 사용
@Repository
public class FilmRepositoryIsA extends FilmDao {

	private final DSLContext dslContext;
	private final JFilm FILM = JFilm.FILM;

	public FilmRepositoryIsA(Configuration configuration, DSLContext dslContext) {
		super(configuration);
		this.dslContext = dslContext;
	}

	public SimpleFilmInfo findSimpleFilmInfoById(Long id) {
		return dslContext.select(FILM.FILM_ID,
				FILM.TITLE,
				FILM.DESCRIPTION)
			.from(FILM)
			.where(FILM.FILM_ID.eq(id))
			.fetchOneInto(SimpleFilmInfo.class);
	}

	public List<FilmWithActor> findFilmWithActors(Long page, Long size) {
		JFilmActor FILM_ACTOR = JFilmActor.FILM_ACTOR;
		JActor ACTOR = JActor.ACTOR;

		return dslContext.select(
				DSL.row(FILM.fields()),
				DSL.row(FILM_ACTOR.fields()),
				DSL.row(ACTOR.fields())
			)
			.from(FILM)
			.innerJoin(FILM_ACTOR).on(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
			.innerJoin(ACTOR).on(ACTOR.ACTOR_ID.eq(FILM_ACTOR.ACTOR_ID))
			.offset((page -1) * size)
			.limit(size)
			.fetchInto(FilmWithActor.class);
	}
}
