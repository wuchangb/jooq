package com.example.jooq;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.actor.ActorRepository;
import com.example.jooq.actor.ActorUpdateRequest;
import com.example.jooq.tables.pojos.Actor;

@SpringBootTest
public class JooqUpdateTest {

	@Autowired
	ActorRepository actorRepository;

	@Test
	void update_with_pojo() {
		var newActor = new Actor();
		newActor.setFirstName("Tom");
		newActor.setLastName("Cruise");

		Actor actor = actorRepository.saveWithReturning(newActor);

		actor.setFirstName("Suri");
		actorRepository.update(actor);

		Actor updatedActor = actorRepository.findByActorId(actor.getActorId());
		assertThat(updatedActor.getFirstName()).isEqualTo("Suri");
	}

	@Test
	void update_fields() {
		var newActor = new Actor();
		newActor.setFirstName("Tom");
		newActor.setLastName("Cruise");

		Long newActorId = actorRepository.saveWithReturningPkOnly(newActor);

		ActorUpdateRequest request = ActorUpdateRequest.builder()
			.firstName("Suri")
			.build();

		int updatedRowCount = actorRepository.updateWithDto(newActorId, request);
		Actor updatedActor = actorRepository.findByActorId(newActorId);
		assertThat(updatedActor.getFirstName()).isEqualTo("Suri");
	}

	@Test
	void update_with_record() {
		var newActor = new Actor();
		newActor.setFirstName("Tom");
		newActor.setLastName("Cruise");

		Long newActorId = actorRepository.saveWithReturningPkOnly(newActor);

		ActorUpdateRequest request = ActorUpdateRequest.builder()
			.firstName("Suri")
			.build();

		int updatedRowCount = actorRepository.updateWithRecord(newActorId, request);

		Actor updatedActor = actorRepository.findByActorId(newActorId);
		assertThat(updatedActor.getFirstName()).isEqualTo("Suri");
	}

	@Test
	void delete() {
		var newActor = new Actor();
		newActor.setFirstName("Tom");
		newActor.setLastName("Cruise");

		Long newActorId = actorRepository.saveWithReturningPkOnly(newActor);

		int result = actorRepository.delete(newActorId);

		assertThat(result).isEqualTo(1);

	}

	@Test
	void delete_with_record() {
		var newActor = new Actor();
		newActor.setFirstName("Tom");
		newActor.setLastName("Cruise");

		Long newActorId = actorRepository.saveWithReturningPkOnly(newActor);

		int result = actorRepository.deleteWithRecord(newActorId);
		assertThat(result).isEqualTo(1);
	}
}
