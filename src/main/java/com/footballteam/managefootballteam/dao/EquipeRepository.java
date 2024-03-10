package com.footballteam.managefootballteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballteam.managefootballteam.model.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
