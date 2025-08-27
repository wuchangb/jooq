package com.example.jooq;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jooq.actor.ActorRepository;
import com.example.jooq.tables.pojos.Actor;
import com.example.jooq.tables.records.ActorRecord;

@SpringBootTest
public class JooqInsertTest {

	@Autowired
	private ActorRepository actorRepository;

	@Test
	@Transactional
	void insert_dao() {
		Actor actor = new Actor();
		actor.setFirstName("John");
		actor.setLastName("Doe");
		actor.setLastUpdate(LocalDateTime.now());

		actorRepository.saveByDao(actor);

		assertThat(actor.getActorId()).isNotNull();
	}

	@Test
	@Transactional
	void insert_by_record() {
		Actor actor = new Actor();
		actor.setFirstName("John");
		actor.setLastName("Doe");
		actor.setLastUpdate(LocalDateTime.now());

		ActorRecord actorRecord = actorRepository.saveByRecord(actor);

		assertThat(actorRecord.getActorId()).isNotNull();
		assertThat(actor.getActorId()).isNull();

	}

	@Test
	@Transactional
	void insert_with_returning_pk() {
		Actor actor = new Actor();
		actor.setFirstName("John");
		actor.setLastName("Doe");
		actor.setLastUpdate(LocalDateTime.now());

		Long pk = actorRepository.saveWithReturningPkOnly(actor);
		assertThat(pk).isNotNull();
	}

	@Test
	@Transactional
	void insert_with_returning() {
		Actor actor = new Actor();
		actor.setFirstName("John");
		actor.setLastName("Doe");
		actor.setLastUpdate(LocalDateTime.now());

		Actor newActor = actorRepository.saveWithReturning(actor);
		assertThat(newActor).isNotNull();
	}

	@Test
	@Transactional
	void bulk_insert() {
		Actor actor1 = new Actor();
		actor1.setFirstName("John");
		actor1.setLastName("Doe");
		actor1.setLastUpdate(LocalDateTime.now());

		Actor actor2 = new Actor();
		actor2.setFirstName("John");
		actor2.setLastName("Doe");
		actor2.setLastUpdate(LocalDateTime.now());

		actorRepository.bulkInsertWithRows(List.of(actor1, actor2));

	}
}
