package com.footballteam.managefootballteam.mapper;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.footballteam.managefootballteam.model.Equipe;
import com.footballteam.managefootballteam.model.dto.EquipeDTO;
import com.footballteam.managefootballteam.model.dto.JoueurDTO;

public class UtilMapper {

	private static ModelMapper modelMapper = new ModelMapper();

	/**
	 * mapEquipetoEquipeDto: to map the Page<Equipe> to Page<EquipeDto> with its
	 * joueurs
	 * 
	 * @param equipePage
	 * @return Page<EquipeDTO>
	 */
	public static Page<EquipeDTO> mapEquipetoEquipeDto(Page<Equipe> equipePage) {
		return equipePage.map(equipe -> {
			EquipeDTO dto = new EquipeDTO();
			dto.setAcronyme(equipe.getAcronyme());
			dto.setBudget(equipe.getBudget());
			dto.setId(equipe.getId());
			List<JoueurDTO> joueursDto = Optional.ofNullable(equipe.getJoueurs()).orElse(List.of()).stream()
					.map(joueur -> modelMapper.map(joueur, JoueurDTO.class)).toList();
			dto.setJoueurs(joueursDto);
			dto.setNom(equipe.getNom());
			return dto;
		});
	}

	/**
	 * mapEquipetoEquipeDto: Map Equipe to EquipeDto
	 * 
	 * @param equipe
	 * @return EquipeDTO
	 */
	public static EquipeDTO mapEquipetoEquipeDto(Equipe equipe) {
		return modelMapper.map(equipe, EquipeDTO.class);
	}
}
