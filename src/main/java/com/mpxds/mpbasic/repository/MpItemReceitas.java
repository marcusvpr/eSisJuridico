package com.mpxds.mpbasic.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import com.mpxds.mpbasic.model.MpItemReceita;
import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpItemReceitas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public MpItemReceita guardar(MpItemReceita mpItemReceita) {
		try {
			return manager.merge(mpItemReceita);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Esse ITEM RECEITA... já foi alterado anteriormente!");
		}
	}
	
	@MpTransactional
	public void remover(MpItemReceita mpItemReceita) throws MpNegocioException {
		try {
			mpItemReceita = porId(mpItemReceita.getId());
			manager.remove(mpItemReceita);
			manager.flush();
		} catch (PersistenceException e) {
			throw new MpNegocioException("ITEM RECEITA... não pode ser excluído.");
		}
	}
	
	public MpItemReceita porId(Long id) {
		return manager.find(MpItemReceita.class, id);
	}
		
}