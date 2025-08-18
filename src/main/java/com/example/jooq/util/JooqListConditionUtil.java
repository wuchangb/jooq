package com.example.jooq.util;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.util.CollectionUtils;

public class JooqListConditionUtil {
	public static <T>Condition inIfNotEmpty(Field<T> field, List<T> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return DSL.noCondition();
		}
		return field.in(ids);
	}
}
