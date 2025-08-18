package com.example.jooq;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jooq.tables.JActor;

@SpringBootTest
public class JooqTest {

	@Autowired
	DSLContext dslContext;

	@Test
	void test() {
		dslContext.selectFrom(JActor.ACTOR)
			.limit(10)
			.fetch();
	}
}
