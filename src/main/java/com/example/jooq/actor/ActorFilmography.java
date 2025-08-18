package com.example.jooq.actor;

import java.util.List;

import com.example.jooq.tables.pojos.Actor;
import com.example.jooq.tables.pojos.Film;

public record ActorFilmography(
	Actor actor,
	List<Film> films
) {
}
