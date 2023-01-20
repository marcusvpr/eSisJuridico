package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mpxds.mpbasic.model.enums.MpStatus;

@Entity
@Table(name = "mp_grupo")
public class MpGrupo extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	
	private MpStatus mpStatus;

	private List<MpObjeto> mpObjetos = new ArrayList<MpObjeto>();
	
	// ----
	
	@Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable=false, length=80)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public MpStatus getMpStatus() {
		return mpStatus;
	}
	public void setMpStatus(MpStatus mpStatus) {
		this.mpStatus = mpStatus;
	}

	@ManyToMany(cascade = CascadeType.ALL) // , fetch = FetchType.EAGER)
	@JoinTable(name = "mp_grupo_objetoX", joinColumns = @JoinColumn(name="mpGrupo_id"),
									inverseJoinColumns = @JoinColumn(name = "mpObjeto_id"))
	public List<MpObjeto> getMpObjetos() { return mpObjetos; }
	public void setMpObjetos(List<MpObjeto> mpObjetos) { this.mpObjetos = mpObjetos; }
	
	public void adicionarMpObjetoVazio() {
		//
		MpObjeto mpObjeto = new MpObjeto();
	
		this.getMpObjetos().add(0, mpObjeto);
	}

	public void removerMpObjetoVazio() {
		//
		MpObjeto mpPrimeiroObjeto = this.getMpObjetos().get(0);
		//
		if (mpPrimeiroObjeto != null)
			this.getMpObjetos().remove(0);
	}

}