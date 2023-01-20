package com.mpxds.mpbasic.model.engreq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.enums.engreq.MpStatusRequisito;

@Entity
@Audited
@AuditTable(value="mp_er_projeto_")
@Table(name = "mp_er_projeto")
public class MpProjeto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;

	private BigDecimal valorHoraEquipe = BigDecimal.ZERO;
	private BigDecimal valorFinalProjeto = BigDecimal.ZERO;
	private BigDecimal qtdProfissional = BigDecimal.ONE;

	private BigDecimal valorHoraEquipePre = BigDecimal.ZERO;
	private BigDecimal valorFinalProjetoPre = BigDecimal.ZERO;
	private BigDecimal qtdProfissionalPre = BigDecimal.ONE;
	private BigDecimal prazoProjetoPre = BigDecimal.ZERO;
		
	private MpStatusRequisito mpStatusRequisito;
	
	private List<MpMacroRequisito> mpMacroRequisitoList = new ArrayList<>();
	private List<MpModulo> mpModuloList = new ArrayList<>();
	private List<MpFuncionalidade> mpFuncionalidadeList = new ArrayList<>();
	private List<MpRequisitoFuncional> mpRequisitoFuncionalList = new ArrayList<>();
	private List<MpRegraNegocio> mpRegraNegocioList = new ArrayList<>();
	private List<MpRequisitoNaoFuncional> mpRequisitoNaoFuncionalList = new ArrayList<>();
	private List<MpProjetoPessoaER> mpProjetoPessoaERList = new ArrayList<>();
		
	// ---

	@Column(nullable=false, length=200)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Lob
	@Column(nullable = false, length = 10000)
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	@Column(name = "valor_Hora_Equipe", nullable = true, precision = 7, scale = 2)
	public BigDecimal getValorHoraEquipe() { return valorHoraEquipe; }
	public void setValorHoraEquipe(BigDecimal valorHoraEquipe) { 
															this.valorHoraEquipe = valorHoraEquipe; }

	@Column(name = "valor_Final_Projeto", nullable = true, precision = 9, scale = 2)
	public BigDecimal getValorFinalProjeto() { return valorFinalProjeto; }
	public void setValorFinalProjeto(BigDecimal valorFinalProjeto) { 
														this.valorFinalProjeto = valorFinalProjeto; }

	@Column(name = "qtd_profissional", nullable = true, precision = 5, scale = 0)
	public BigDecimal getQtdProfissional() { return qtdProfissional; }
	public void setQtdProfissional(BigDecimal qtdProfissional) { 
															this.qtdProfissional = qtdProfissional; }

	@Column(name = "valor_Hora_Equipe_Pre", nullable = true, precision = 7, scale = 2)
	public BigDecimal getValorHoraEquipePre() { return valorHoraEquipePre; }
	public void setValorHoraEquipePre(BigDecimal valorHoraEquipePre) { 
													this.valorHoraEquipePre = valorHoraEquipePre; }

	@Column(name = "valor_Final_Projeto_Pre", nullable = true, precision = 9, scale = 2)
	public BigDecimal getValorFinalProjetoPre() { return valorFinalProjetoPre; }
	public void setValorFinalProjetoPre(BigDecimal valorFinalProjetoPre) { 
												this.valorFinalProjetoPre = valorFinalProjetoPre; }

	@Column(name = "qtd_profissional_Pre", nullable = true, precision = 5, scale = 0)
	public BigDecimal getQtdProfissionalPre() { return qtdProfissionalPre; }
	public void setQtdProfissionalPre(BigDecimal qtdProfissionalPre) { 
													this.qtdProfissionalPre = qtdProfissionalPre; }

	@Column(name = "prazo_Projeto_Pre", nullable = true, precision = 7, scale = 3)
	public BigDecimal getPrazoProjetoPre() { return prazoProjetoPre; }
	public void setPrazoProjetoPre(BigDecimal prazoProjetoPre) { 
												this.prazoProjetoPre = prazoProjetoPre; }
	
	// ---

	@NotNull(message = "Por favor, informe o STATUS")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	public MpStatusRequisito getMpStatusRequisito() { return mpStatusRequisito; }
	public void setMpStatusRequisito(MpStatusRequisito mpStatusRequisito) {
														this.mpStatusRequisito = mpStatusRequisito; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpMacroRequisito> getMpMacroRequisitoList() { return mpMacroRequisitoList; }
	public void setMpMacroRequisitoList(List<MpMacroRequisito> mpMacroRequisitoList) {
												this.mpMacroRequisitoList = mpMacroRequisitoList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpModulo> getMpModuloList() { return mpModuloList; }
	public void setMpModuloList(List<MpModulo> mpModuloList) { this.mpModuloList = mpModuloList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpFuncionalidade> getMpFuncionalidadeList() { return mpFuncionalidadeList; }
	public void setMpFuncionalidadeList(List<MpFuncionalidade> mpFuncionalidadeList) {
													this.mpFuncionalidadeList = mpFuncionalidadeList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpRequisitoFuncional> getMpRequisitoFuncionalList() { 
																	return mpRequisitoFuncionalList; }
	public void setMpRequisitoFuncionalList(List<MpRequisitoFuncional> mpRequisitoFuncionalList) {
										this.mpRequisitoFuncionalList = mpRequisitoFuncionalList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpRegraNegocio> getMpRegraNegocioList() { return mpRegraNegocioList; }
	public void setMpRegraNegocioList(List<MpRegraNegocio> mpRegraNegocioList) {
													this.mpRegraNegocioList = mpRegraNegocioList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpRequisitoNaoFuncional> getMpRequisitoNaoFuncionalList() { 
																return mpRequisitoNaoFuncionalList; }
	public void setMpRequisitoNaoFuncionalList(List<MpRequisitoNaoFuncional> 
																	mpRequisitoNaoFuncionalList) {
												this.mpRequisitoNaoFuncionalList = mpRequisitoNaoFuncionalList; }
	
	@OneToMany(mappedBy = "mpProjeto", cascade = CascadeType.ALL)
	public List<MpProjetoPessoaER> getMpProjetoPessoaERList() { return mpProjetoPessoaERList; }
	public void setMpProjetoPessoaERList(List<MpProjetoPessoaER> mpProjetoPessoaERList) {
															this.mpProjetoPessoaERList = mpProjetoPessoaERList; }
		
	// ---
	
	@Transient
	public Integer getMacroRequisitoSize() {
		return this.mpMacroRequisitoList.size(); }
	
	@Transient
	public Integer getModuloSize() {
		return this.mpModuloList.size(); }
	
	@Transient
	public Integer getFuncionalidadeSize() {
		return this.mpFuncionalidadeList.size(); }
	
	@Transient
	public Integer getRequisitoFuncionalSize() {
		return this.mpRequisitoFuncionalList.size(); }
	
	@Transient
	public Integer getRegraNegocioSize() {
		return this.mpRegraNegocioList.size(); }
	
	@Transient
	public Integer getRequisitoNaoFuncionalSize() {
		return this.mpRequisitoNaoFuncionalList.size(); }
	
	@Transient
	public Integer getProjetoPessoaERSize() {
		return this.mpProjetoPessoaERList.size(); }
	
}