package com.mpxds.mpbasic.repository.sisJuri;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.sisJuri.MpPessoaSJ;

public class MpPessoaSJs implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// ---
	
	public MpPessoaSJ guardar(MpPessoaSJ mpPessoaSJ) {
		try {
			return manager.merge(mpPessoaSJ);
		} catch (OptimisticLockException e) {
			throw new MpNegocioException(
					"Erro de concorrência. Essa PESSOA... já foi alterada anteriormente!");
		}
	}

	public List<MpPessoaSJ> byNomeList() {
		return this.manager.createQuery("from MpPessoaSJ ORDER BY nome", MpPessoaSJ.class)
					.getResultList();
	}
	public List<MpPessoaSJ> byNomeParteContrariaList() {
		return this.manager.createQuery("from MpPessoaSJ where indParteContraria = :indPC ORDER BY nome",
				MpPessoaSJ.class).setParameter("indPC", true).getResultList();
	}
	
	public MpPessoaSJ porId(Long id) {
		//
		return manager.find(MpPessoaSJ.class, id);
	}
	public MpPessoaSJ porIdCarga(Long idCarga) {
		//
		try {
			return manager.createQuery("from MpPessoaSJ where idCarga = :idCarga", MpPessoaSJ.class)
				.setParameter("idCarga", idCarga).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}