package com.example.jooq.film;

import com.example.jooq.tables.pojos.Actor;
import com.example.jooq.tables.pojos.Film;
import com.example.jooq.tables.pojos.FilmActor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FilmWithActor {
	private final Film film;
	private final FilmActor filmActor;
	private final Actor actor;

	public String getTitle() {
		return film.getTitle();
	}

	public String getActorFullName() {
		return actor.getFirstName() + " " + actor.getLastName();
	}

	public Long getFilmId() {
		return film.getFilmId();
	}
}
