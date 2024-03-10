package com.footballteam.managefootballteam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballteam.managefootballteam.model.Joueur;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {

	public List<Joueur> findAllByEquipeId(Long equipeId);
}
