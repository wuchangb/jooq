package com.example.jooq.web;

import java.util.List;

import com.example.jooq.film.FilmWithActor;

public record FilmWithActorPagedResponse(
	PagedResponse pagedResponse,
	List<FilmActorResponse> filmActors
) {

	public static FilmWithActorPagedResponse of(PagedResponse pagedResponse, List<FilmWithActor> filmWithActors) {
		return new FilmWithActorPagedResponse(pagedResponse,
			filmWithActors.stream().map(FilmActorResponse::of).toList());
	}

	public record FilmActorResponse(
		String filmTitle,
		String actorFullName,
		Long filmId
	) {

		public static FilmActorResponse of(FilmWithActor filmWithActor) {
			return new FilmActorResponse(
				filmWithActor.getTitle(),
				filmWithActor.getActorFullName(),
				filmWithActor.getFilmId());
		}

	}
}
