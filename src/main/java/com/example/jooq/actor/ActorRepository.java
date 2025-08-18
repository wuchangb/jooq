package com.example.jooq.actor;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.jooq.tables.JActor;
import com.example.jooq.tables.daos.ActorDao;
import com.example.jooq.tables.pojos.Actor;

@Repository
public class ActorRepository {

	private final DSLContext dslContext;
	private final ActorDao actorDao;
	private final JActor ACTOR = JActor.ACTOR;

	public ActorRepository(DSLContext dslContext, Configuration configuration) {
		this.dslContext = dslContext;
		this.actorDao = new ActorDao(configuration);
	}

	public List<Actor> findByFirstNameAndLastName(String firstName, String lastName) {
		return dslContext.selectFrom(ACTOR)
			.where(
				ACTOR.FIRST_NAME.eq(firstName),
				ACTOR.LAST_NAME.eq(lastName)
			)
			.fetchInto(Actor.class);
	}

	public List<Actor> findByFirstNameOrLastName(String firstName, String lastName) {
		return dslContext.selectFrom(ACTOR)
			.where(
				ACTOR.FIRST_NAME.eq(firstName)
					.or(ACTOR.LAST_NAME.eq(lastName))
			)
			.fetchInto(Actor.class);
	}
}
