package com.example.jooq.actor;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.tables.pojos.Actor;

@SpringBootTest
class ActorRepositoryTest {

	@Autowired
	private ActorRepository actorRepository;

	@Test
	void test_1() {
		String firstName = "ED";
		String lastName = "CHASE";

		List<Actor> actors = actorRepository.findByFirstNameAndLastName(firstName, lastName);

		Assertions.assertThat(actors).hasSize(1);
	}

	@Test
	void test_2() {
		String firstName = "ED";
		String lastName = "CHASE";

		List<Actor> actors = actorRepository.findByFirstNameOrLastName(firstName, lastName);

		Assertions.assertThat(actors).hasSizeGreaterThan(1);
	}

	@Test
	void test_3() {
		List<Actor> actors = actorRepository.findByActorIdIn(List.of(1L));

		Assertions.assertThat(actors).hasSize(1);
	}

	@Test
	void test_4() {
		List<Actor> actors = actorRepository.findByActorIdIn(Collections.EMPTY_LIST);

		Assertions.assertThat(actors).hasSizeGreaterThan(1);
	}

	@Test
	void test_5() {
		var searchOption = ActorFilmographySearchCondition.builder()
			.actorName("LOLLOBRIGIDA")
			.build();

		List<ActorFilmography> actorFilmographies = actorRepository.findActorFilmography(searchOption);
		Assertions.assertThat(actorFilmographies).hasSize(1);
	}

	@Test
	void test_6() {
		var searchOption = ActorFilmographySearchCondition.builder()
			.actorName("LOLLOBRIGIDA")
			.fileTitle("COMMANDMENTS EXPRESS")
			.build();

		List<ActorFilmography> actorFilmographies = actorRepository.findActorFilmography(searchOption);
		Assertions.assertThat(actorFilmographies).hasSize(1);
	}
}