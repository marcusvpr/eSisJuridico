package com.mpxds.mpbasic.model.engreq;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.engreq.MpPessoaFuncao;

@Entity
@Audited
@AuditTable(value="mp_er_projeto_pessoa_")
@Table(name = "mp_er_projeto_pessoa")
public class MpProjetoPessoaER extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
		
	private MpPessoaER mpPessoaER;
	private MpPessoaFuncao mpPessoaFuncao;
	
	private String observacao;
	
	private MpProjeto mpProjeto;
	
	// ---

	@NotNull
	@ManyToOne
	@JoinColumn(name = "mpPessoaER_id", nullable = false)
	public MpPessoaER getMpPessoaER() { return mpPessoaER; }
	public void setMpPessoaER(MpPessoaER mpPessoaER) { this.mpPessoaER = mpPessoaER; }
		
	@NotNull(message = "Por favor, informe a FUNÇÃO")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpPessoaFuncao getMpPessoaFuncao() { return mpPessoaFuncao; }
	public void setMpPessoaFuncao(MpPessoaFuncao mpPessoaFuncao) { this.mpPessoaFuncao = mpPessoaFuncao; }

	@Column(nullable = true, length=200)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }
		
	@ManyToOne
	@JoinColumn(name = "mpProjeto_id", nullable = false)
	public MpProjeto getMpProjeto() { return mpProjeto; }
	public void setMpProjeto(MpProjeto mpProjeto) { this.mpProjeto = mpProjeto; }
		
}