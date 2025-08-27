package com.example.jooq;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.jooq.impl.DefaultDSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.actor.ActorRepository;
import com.example.jooq.tables.JActor;
import com.example.jooq.tables.records.ActorRecord;

@SpringBootTest
public class JooqActiveRecordTest {

	@Autowired
	ActorRepository actorRepository;
	@Autowired
	private DefaultDSLContext dslContext;

	@Test
	void activeRecord_select() {
		Long actorId = 1L;

		ActorRecord actorRecord = actorRepository.findRecordByActorId(actorId);

		assertThat(actorRecord).hasNoNullFieldsOrProperties();
	}

	@Test
	void activeRecord_refresh() {
		Long actorId = 1L;

		ActorRecord actorRecord = actorRepository.findRecordByActorId(actorId);
		actorRecord.setFirstName("null");

		actorRecord.refresh();
		assertThat(actorRecord.getFirstName()).isNotBlank();
	}

	@Test
	void activeRecord_insert() {
		ActorRecord actorRecord = dslContext.newRecord(JActor.ACTOR);
		actorRecord.setFirstName("John");
		actorRecord.setLastName("Doe");

		actorRecord.store();
		actorRecord.refresh();

		assertThat(actorRecord.getLastUpdate()).isNotNull();
	}

	@Test
	void activeRecord_update() {
		Long actorId = 1L;
		String newName = "test";
		ActorRecord actorRecord = actorRepository.findRecordByActorId(actorId);

		actorRecord.setFirstName(newName);
		actorRecord.store();

		assertThat(actorRecord.getFirstName()).isEqualTo(newName);
	}

	@Test
	void activeRecord_delete() {
		ActorRecord actorRecord = dslContext.newRecord(JActor.ACTOR);
		actorRecord.setFirstName("John");
		actorRecord.setLastName("Doe");
		actorRecord.store();

		int result = actorRecord.delete();
		assertThat(result).isEqualTo(1);
	}
}
