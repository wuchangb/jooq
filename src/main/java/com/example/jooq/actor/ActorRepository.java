package com.example.jooq.actor;

import static com.example.jooq.util.JooqListConditionUtil.*;
import static org.jooq.impl.DSL.*;

import java.util.List;
import java.util.Map;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Row2;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jooq.tables.JActor;
import com.example.jooq.tables.JFilm;
import com.example.jooq.tables.JFilmActor;
import com.example.jooq.tables.daos.ActorDao;
import com.example.jooq.tables.pojos.Actor;
import com.example.jooq.tables.pojos.Film;
import com.example.jooq.tables.records.ActorRecord;

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

	public List<Actor> findByActorIdIn(List<Long> ids) {
		return dslContext.selectFrom(ACTOR)
			.where(
				inIfNotEmpty(ACTOR.ACTOR_ID, ids)
			)
			.fetchInto(Actor.class);
	}

	public List<ActorFilmography> findActorFilmography(ActorFilmographySearchCondition searchOption) {
		final JFilmActor FILM_ACTOR = JFilmActor.FILM_ACTOR;
		final JFilm FILM = JFilm.FILM;

		Map<Actor, List<Film>> actorMap = dslContext.select(
				DSL.row(ACTOR.fields()).as("actor"),
				DSL.row(FILM.fields()).as("film")
			)
			.from(ACTOR)
			.join(FILM_ACTOR).on(FILM_ACTOR.ACTOR_ID.eq(ACTOR.ACTOR_ID))
			.join(FILM).on(FILM.FILM_ID.eq(FILM_ACTOR.FILM_ID))
			.where(
				containsIfNotBlank(ACTOR.FIRST_NAME.concat(" ").concat(ACTOR.LAST_NAME), searchOption.getActorName()),
				containsIfNotBlank(FILM.TITLE, searchOption.getFileTitle())

				)
			.fetchGroups(
				record -> record.get("actor", Actor.class),
				record -> record.get("film", Film.class)
			);

		return actorMap.entrySet().stream()
			.map(entry -> new ActorFilmography(entry.getKey(), entry.getValue()))
			.toList();
	}

	private Condition containsIfNotBlank(Field<String> field, String inputValue) {
		if (inputValue == null || inputValue.isBlank()) {
			return DSL.noCondition();
		}
		return field.like("%" + inputValue + "%");
	}

	public Actor saveByDao(Actor actor) {
		actorDao.insert(actor);
		return actor;
	}

	public ActorRecord saveByRecord(Actor actor) {
		ActorRecord actorRecord = dslContext.newRecord(ACTOR, actor);
		actorRecord.insert();
		return actorRecord;
	}

	public Long saveWithReturningPkOnly(Actor actor) {
		return dslContext.insertInto(
			ACTOR, ACTOR.FIRST_NAME, ACTOR.LAST_NAME
		).values(
			actor.getFirstName(),
			actor.getLastName()
		).returningResult(ACTOR.ACTOR_ID)
			.fetchOneInto(Long.class);
	}

	public Actor saveWithReturning(Actor actor) {
		return dslContext.insertInto(
			ACTOR, ACTOR.FIRST_NAME, ACTOR.LAST_NAME
		).values(
			actor.getFirstName(),
			actor.getLastName()
		).returning()
			.fetchOneInto(Actor.class);
	}

	public void bulkInsertWithRows(List<Actor> actorList) {

		var rows = actorList.stream()
			.map(actor -> DSL.row(
				actor.getFirstName(),
				actor.getLastName()
			))
			.toList();

		dslContext.insertInto(
			ACTOR, ACTOR.FIRST_NAME, ACTOR.LAST_NAME
		).valuesOfRows(rows)
			.execute();
	}

	public void update(Actor actor) {
		actorDao.update(actor);
	}

	public Actor findByActorId(Long actorId) {
		return actorDao.findById(actorId);
	}

	public int updateWithDto(Long newActorId, ActorUpdateRequest request) {
		var firstName = StringUtils.hasText(request.firstName()) ? val(request.firstName()) : DSL.noField(ACTOR.FIRST_NAME);
		var lastName = StringUtils.hasText(request.lastName()) ? val(request.lastName()) : DSL.noField(ACTOR.LAST_NAME);
		return dslContext.update(ACTOR)
			.set(ACTOR.FIRST_NAME, firstName)
			.set(ACTOR.LAST_NAME, lastName)
			.where(ACTOR.ACTOR_ID.eq(newActorId))
			.execute();
	}

	public int updateWithRecord(Long newActorId, ActorUpdateRequest request) {
		ActorRecord record = dslContext.fetchOne(ACTOR, ACTOR.ACTOR_ID.eq(newActorId));

		if (StringUtils.hasText(request.firstName())) {
			record.setFirstName(request.firstName());
		}

		if (StringUtils.hasText(request.lastName())) {
			record.setLastName(request.lastName());
		}

		return dslContext.update(ACTOR)
			.set(record)
			.where(ACTOR.ACTOR_ID.eq(newActorId))
			.execute();
	}

	public int delete(Long newActorId) {
		return dslContext.delete(ACTOR)
			.where(ACTOR.ACTOR_ID.eq(newActorId))
			.execute();
	}

	public int deleteWithRecord(Long newActorId) {
		ActorRecord record = dslContext.fetchOne(ACTOR, ACTOR.ACTOR_ID.eq(newActorId));
		return record.delete();
	}

	public ActorRecord findRecordByActorId(Long actorId) {
		return dslContext.fetchOne(ACTOR, ACTOR.ACTOR_ID.eq(actorId));
	}
}
