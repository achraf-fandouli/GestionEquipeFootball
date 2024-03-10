package com.footballteam.managefootballteam.service;

import org.springframework.data.domain.Page;

import com.footballteam.managefootballteam.enums.SortEnum;
import com.footballteam.managefootballteam.model.Equipe;

public interface EquipeService {

	/**
	 * getEquipeById: Service to retrieve equipe by id
	 * 
	 * @param id : the equipe id
	 * @return Equipe
	 */
	public Equipe getEquipeById(Long id);

	/**
	 * createEquipe: Service to create new equipe
	 * 
	 * @param equipe
	 * @return Equipe
	 */
	public Equipe createEquipe(Equipe equipe);

	/**
	 * getEquipesByOrderAsc: Service to retrieve all equipes sorted by nom and
	 * acronyme and budget and ordered ASC
	 * 
	 * @param page : the page number
	 * @param size : the page size
	 * @return Page<Equipe>
	 */
	public Page<Equipe> getEquipesByOrderAsc(int page, int size);

	/**
	 * getEquipesByGenericOrder: Service to retrieve all equipes sorted by nom
	 * or(and) acronyme or (and) budget and order by ASC or DESC
	 * 
	 * @param page         : the page number
	 * @param size         : the size of the page
	 * @param sortNom      : name sort order
	 * @param sortAcronyme : acronyme sort order
	 * @param sortBudget   : budget sort order
	 * @return Page<Equipe>
	 */
	Page<Equipe> getEquipesByGenericOrder(int page, int size, SortEnum sortNom, SortEnum sortAcronyme,
			SortEnum sortBudget);
}
