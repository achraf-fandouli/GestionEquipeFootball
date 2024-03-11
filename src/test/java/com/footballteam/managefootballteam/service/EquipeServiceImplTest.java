package com.footballteam.managefootballteam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.footballteam.managefootballteam.dao.EquipeRepository;
import com.footballteam.managefootballteam.enums.SortEnum;
import com.footballteam.managefootballteam.exception.EquipeException;
import com.footballteam.managefootballteam.exception.EquipeException.CodeErrorException;
import com.footballteam.managefootballteam.model.Equipe;
import com.footballteam.managefootballteam.service.serviceImpl.EquipeServiceImpl;

@SpringBootTest
public class EquipeServiceImplTest {

	@MockBean
	private EquipeRepository equipeRepository;

	@Autowired
	private EquipeServiceImpl equipeService;

	@Test
	public void testCreateEquipe() {
		// Given
		Equipe equipe = new Equipe();
		equipe.setNom("Test Team");
		equipe.setAcronyme("TT");
		equipe.setBudget(10L);

		// When
		when(equipeRepository.save(Mockito.any())).thenReturn(equipe);
		Equipe createdEquipe = equipeService.createEquipe(equipe);

		// Then
		assertNotNull(createdEquipe);
		assertEquals("Test Team", createdEquipe.getNom());
		assertEquals("TT", createdEquipe.getAcronyme());
		assertEquals(10L, createdEquipe.getBudget());
	}

	@Test
	void testCreateEquipe_Not_Ok() {
		// Given
		Equipe equipe = new Equipe();
		equipe.setAcronyme("TT");
		equipe.setBudget(10L);

		// When
		when(equipeRepository.save(Mockito.any())).thenReturn(new RuntimeException());
		try {
			equipeService.createEquipe(equipe);

		} catch (EquipeException ex) {
			assertEquals(CodeErrorException.ERROR_CREATING_EQUIPE.getCodeNumber(),
					ex.getCodeErrorException().getCodeNumber());
		}
	}

	@Test
	void testGetEquipesByOrderAsc() {
		// Given
		Equipe equipe1 = new Equipe(null, "A", "EQA", 100L, null);
		Equipe equipe2 = new Equipe(null, "B", "EQB", 1000L, null);

		// When
		List<Equipe> list = List.of(equipe1, equipe2);
		Page<Equipe> equipes = new PageImpl<>(list.subList(0, 2), PageRequest.of(0, 10), list.size());
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nom")
				.and(Sort.by(Sort.Direction.ASC, "acronyme")).and(Sort.by(Sort.Direction.ASC, "budget")));
		when(equipeRepository.findAll(pageable)).thenReturn(equipes);

		// Act
		Page<Equipe> sortedEquipeList = equipeService.getEquipesByOrderAsc(0, 10);

		String expectedEquipeName = "A";

		// Then
		assertNotNull(sortedEquipeList);
		assertEquals(2, sortedEquipeList.getNumberOfElements());
		assertEquals(expectedEquipeName, sortedEquipeList.getContent().get(0).getNom());
	}

	@Test
	void testGetEquipesByOrderAsc_Not_Ok() {

		// Given
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nom")
				.and(Sort.by(Sort.Direction.ASC, "acronyme")).and(Sort.by(Sort.Direction.ASC, "budget")));

		// When
		when(equipeRepository.findAll(pageable)).thenThrow(new RuntimeException());
		// Then
		try {
			equipeService.getEquipesByOrderAsc(0, 10);

		} catch (EquipeException ex) {
			assertEquals(CodeErrorException.ERROR_RETRIEVING_EQUIPES.getCodeNumber(),
					ex.getCodeErrorException().getCodeNumber());
		}
	}

	@Test
	void testGetEquipesByGenericOrder() {

		Equipe equipe1 = new Equipe(null, "A", "EQA", 100L, null);
		Equipe equipe2 = new Equipe(null, "B", "EQB", 1000L, null);

		// When
		List<Equipe> list = List.of(equipe1, equipe2);
		Page<Equipe> equipes = new PageImpl<>(list.subList(0, 2), PageRequest.of(0, 10), list.size());
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nom"));
		when(equipeRepository.findAll(pageable)).thenReturn(equipes);

		// Act
		Page<Equipe> sortedEquipeList = equipeService.getEquipesByGenericOrder(0, 10, SortEnum.ASC, null, null);

		String expectedEquipeName = "A";

		// Then
		assertNotNull(sortedEquipeList);
		assertEquals(2, sortedEquipeList.getNumberOfElements());
		assertEquals(expectedEquipeName, sortedEquipeList.getContent().get(0).getNom());
	}

	@Test
	void testGetEquipesByGenericOrder_Not_Ok() {

		// Given
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "nom"));

		// When
		when(equipeRepository.findAll(pageable)).thenThrow(new RuntimeException());

		// Act
		try {
			equipeService.getEquipesByGenericOrder(0, 10, SortEnum.DESC, SortEnum.DESC, SortEnum.DESC);

		} catch (EquipeException ex) {
			assertEquals(CodeErrorException.ERROR_RETRIEVING_EQUIPES.getCodeNumber(),
					ex.getCodeErrorException().getCodeNumber());
		}
	}
}