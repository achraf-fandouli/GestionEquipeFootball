package com.footballteam.managefootballteam.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Equipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "The name required")
	private String nom;

	@NotNull(message = "The acronym required")
	private String acronyme;

	@NotNull(message = "The budget required")
	private Long budget;

	@OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
	private List<Joueur> joueurs;
}
