package com.example.jooq.web;

public record PagedResponse(
	long page,
	long size
) {
}
