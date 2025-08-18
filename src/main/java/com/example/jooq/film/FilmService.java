package com.example.jooq.film;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jooq.web.FilmWithActorPagedResponse;
import com.example.jooq.web.PagedResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {

	private final FilmRepository filmRepository;

	public FilmWithActorPagedResponse getFilmActorPageResponse(Long page, Long size) {
		List<FilmWithActor> filmWithActors = filmRepository.findFilmWithActors(page, size);
		PagedResponse pagedResponse = new PagedResponse(page, size);
		return FilmWithActorPagedResponse.of(pagedResponse,filmWithActors);
	}
}
