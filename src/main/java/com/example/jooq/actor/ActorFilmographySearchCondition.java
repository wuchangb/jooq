package com.example.jooq.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorFilmographySearchCondition {
	private String actorName;
	private String fileTitle;

}
