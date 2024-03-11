package com.footballteam.managefootballteam.integrationtests.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footballteam.managefootballteam.model.Equipe;

public interface TestH2Repository extends JpaRepository<Equipe, Long> {

}
