package com.mpxds.mpbasic.model.livroDiario;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mpxds.mpbasic.model.MpBaseEntity;

import javax.persistence.Column;

@Entity
@Table(name = "mp_ld_tipo_lancamento")
public class MpTipoLancamento extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String descricao;

	// ---

	public MpTipoLancamento() {
		super();
	}

	public MpTipoLancamento(String descricao) {
		super();
		//
		this.descricao = descricao;
	}

	@Column(nullable = false, length = 100)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

}