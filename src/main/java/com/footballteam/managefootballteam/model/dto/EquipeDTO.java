package com.footballteam.managefootballteam.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipeDTO {

	@Schema(description = "Unique identifier", example = "1")
	private Long id;
	@Schema(description = "equipe name", example = " OGC NICE")
	private String nom;
	@Schema(description = "equipe acronyme", example = "OGC")
	private String acronyme;
	@Schema(description = "equipe budget", example = "10000")
	private Long budget;
	@Schema(description = "equipe palyers")
	private List<JoueurDTO> joueurs;
}
