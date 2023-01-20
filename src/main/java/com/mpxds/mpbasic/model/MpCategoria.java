package com.mpxds.mpbasic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.enums.MpTipoProduto;

@Entity
@Table(name = "mp_categoria")
public class MpCategoria extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private MpTipoProduto mpTipoProduto;
	
	private String descricao;
	
	private MpCategoria mpCategoriaPai;
	
	private List<MpCategoria> mpSubcategorias = new ArrayList<>();

	// ---

	@NotNull(message = "Por favor, informe o Tipo")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	public MpTipoProduto getMpTipoProduto() { return mpTipoProduto; }
	public void setMpTipoProduto(MpTipoProduto mpTipoProduto) {	this.mpTipoProduto = mpTipoProduto; }
	
	@NotBlank(message = "Por favor, informe a DESCRIÇÃO")
	@Column(nullable = false, length = 60)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@ManyToOne
	@JoinColumn(name = "mpCategoria_pai_id")
	public MpCategoria getMpCategoriaPai() { return mpCategoriaPai; }
	public void setMpCategoriaPai(MpCategoria mpCategoriaPai) { 
															this.mpCategoriaPai = mpCategoriaPai; }

	@OneToMany(mappedBy = "mpCategoriaPai", cascade = CascadeType.ALL)
	public List<MpCategoria> getMpSubcategorias() { return mpSubcategorias; }
	public void setMpSubcategorias(List<MpCategoria> mpSubcategorias) {
															this.mpSubcategorias = mpSubcategorias; }
	
	//

	@Transient
	public String getTipoProdutoDescricao() {
		//
	    return this.mpTipoProduto.getDescricao().trim() + "." + this.descricao.trim();
	}

}