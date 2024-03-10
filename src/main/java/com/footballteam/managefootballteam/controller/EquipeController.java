package com.footballteam.managefootballteam.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footballteam.managefootballteam.enums.SortEnum;
import com.footballteam.managefootballteam.mapper.UtilMapper;
import com.footballteam.managefootballteam.model.Equipe;
import com.footballteam.managefootballteam.model.dto.EquipeDTO;
import com.footballteam.managefootballteam.service.EquipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

	private EquipeService equipeService;

	private static ModelMapper modelMapper = new ModelMapper();

	public EquipeController(EquipeService equipeService) {
		this.equipeService = equipeService;
	}

	@GetMapping("/genericSort")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully obtains equipes"),
			@ApiResponse(responseCode = "400", description = "Error when finding equipes", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))

	})
	@Operation(summary = "API to retrieve all equipes sorted by nom or(and) acronyme or (and) budget by order ASC or DESC")
	public ResponseEntity<Page<EquipeDTO>> findEquipes(
			@RequestParam(defaultValue = "0", name = "current page") int page,
			@RequestParam(defaultValue = "10", name = "number of elements") int size,
			@Nullable @RequestParam(value = "nom") SortEnum sortNom,
			@Nullable @RequestParam(value = "acronyme") SortEnum sortAcronyme,
			@Nullable @RequestParam(value = "budget") SortEnum sortBudget) {

		Page<Equipe> equipePage = equipeService.getEquipesByGenericOrder(page, size, sortNom, sortAcronyme, sortBudget);
		Page<EquipeDTO> equipeDTOList = UtilMapper.mapEquipetoEquipeDto(equipePage);

		return ResponseEntity.ok(equipeDTOList);
	}

	@GetMapping("/simpleSort")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully obtains equipes"),
			@ApiResponse(responseCode = "400", description = "Error when finding equipes", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))

	})
	@Operation(summary = "API to retrieve all equipes sorted by nom and acronyme and budget by order ASC")
	public ResponseEntity<Page<EquipeDTO>> findEquipesByOrderAsc(
			@RequestParam(defaultValue = "0", name = "current page") int page,
			@RequestParam(defaultValue = "10", name = "number of elements") int size) {

		Page<Equipe> equipePage = equipeService.getEquipesByOrderAsc(page, size);
		Page<EquipeDTO> equipeDTOList = UtilMapper.mapEquipetoEquipeDto(equipePage);
		return ResponseEntity.ok(equipeDTOList);
	}

	@PostMapping
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully insert equipes"),
			@ApiResponse(responseCode = "400", description = "Input values are not respecting some rules", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))

	})
	@Operation(summary = "API Create equipe with or without joueurs")
	public ResponseEntity<EquipeDTO> createEquipe(@RequestBody EquipeDTO equipeDTO) {
		Equipe equipe = modelMapper.map(equipeDTO, Equipe.class);
		return ResponseEntity.ok(UtilMapper.mapEquipetoEquipeDto(equipeService.createEquipe(equipe)));

	}

	@GetMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully obtains equipes"),
			@ApiResponse(responseCode = "400", description = "Error when finding equipes", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))

	})
	@Operation(summary = "API to retrieve equipe by id")
	public ResponseEntity<EquipeDTO> findById(@PathVariable(name = "id") Long id) {

		Equipe equipe = equipeService.getEquipeById(id);
		EquipeDTO equipeDTO = UtilMapper.mapEquipetoEquipeDto(equipe);
		return ResponseEntity.ok(equipeDTO);
	}

}
