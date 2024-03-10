package com.footballteam.managefootballteam.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.footballteam.managefootballteam.dao.EquipeRepository;
import com.footballteam.managefootballteam.enums.SortEnum;
import com.footballteam.managefootballteam.exception.EquipeException;
import com.footballteam.managefootballteam.exception.EquipeException.CodeErrorException;
import com.footballteam.managefootballteam.model.Equipe;
import com.footballteam.managefootballteam.service.EquipeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EquipeServiceImpl implements EquipeService {

	private static final String ERROR_CREATING_EQUIPE = "Error creating équipe: ";
	private static final String ERROR_RETRIEVING_EQUIPES = "Error retrieving équipes: ";
	private EquipeRepository equipeRepository;

	@Override
	public Page<Equipe> getEquipesByOrderAsc(int page, int size) {
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nom")
					.and(Sort.by(Sort.Direction.ASC, "acronyme")).and(Sort.by(Sort.Direction.ASC, "budget")));
			return equipeRepository.findAll(pageable);
		} catch (Exception e) {
			log.warn(ERROR_RETRIEVING_EQUIPES + e);
			throw new EquipeException(CodeErrorException.ERROR_RETRIEVING_EQUIPES, e.getMessage());
		}
	}

	@Override
	public Page<Equipe> getEquipesByGenericOrder(int page, int size, SortEnum sortNom, SortEnum sortAcronyme,
			SortEnum sortBudget) {
		try {
			List<Sort.Order> orders = new ArrayList<>();
			if (sortNom != null) {
				orders.add(new Sort.Order((sortNom.equals(SortEnum.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC),
						"nom"));
			}
			if (sortAcronyme != null) {
				orders.add(new Sort.Order(
						(sortAcronyme.equals(SortEnum.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC), "acronyme"));
			}
			if (sortBudget != null) {
				orders.add(new Sort.Order((sortBudget.equals(SortEnum.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC),
						"budget"));
			}

			Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
			return equipeRepository.findAll(pageable);
		} catch (DataAccessException e) {
			log.warn(ERROR_RETRIEVING_EQUIPES + e);
			throw new EquipeException(CodeErrorException.ERROR_RETRIEVING_EQUIPES, e.getMessage());
		}
	}

	@Override
	public Equipe getEquipeById(Long id) {
		try {
			return equipeRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Equipe not found with ID: " + id));
		} catch (Exception e) {
			log.warn(ERROR_RETRIEVING_EQUIPES + e);
			throw new EquipeException(CodeErrorException.NOT_FOUND_ELEMENT, "Equipe", id.toString());
		}
	}

	@Override
	public Equipe createEquipe(Equipe equipe) {
		try {
			Optional.ofNullable(equipe.getJoueurs()).orElse(List.of()).stream()
					.forEach(joueur -> joueur.setEquipe(equipe));
			return equipeRepository.save(equipe);
		} catch (Exception e) {
			log.warn(ERROR_CREATING_EQUIPE + e);
			throw new EquipeException(CodeErrorException.ERROR_CREATING_EQUIPE, e.getMessage());
		}
	}
}
