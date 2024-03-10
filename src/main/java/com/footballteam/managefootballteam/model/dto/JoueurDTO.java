package com.footballteam.managefootballteam.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoueurDTO {

	@Schema(description = "Unique identifier", example = "1")
	private Long id;
	@Schema(description = "player name", example = "1")
	private String nom;
	@Schema(description = "Player position", example ="RB")
	private String position;
}
