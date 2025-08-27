package com.example.jooq.actor;

import lombok.Builder;

@Builder
public record ActorUpdateRequest(
	String firstName,
	String lastName
) {
}
