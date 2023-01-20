package com.mpxds.mpbasic.model.webVideo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpBaseEntity;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "mp_wv_artista") // (Base.MFCL035.DBF)
public class MpArtista extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;

	private String nome;
	private String obs;

	private byte[] fotoBD;

	// ---

	public MpArtista() {
		super();
	}

	public MpArtista(String nome, String obs) {
		super();
		this.nome = nome;
		this.obs = obs;
	}

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(min = 5, message = "Nome é muito pequeno(<5)")
	@Column(nullable = false, length = 100)
	public String getNome() { return this.nome; }
	public void setNome(String newNome) { this.nome = newNome; }

	@Length(min = 0, max = 150, message="Obs. deve ter no máximo(150) caracteres")
	@Column(nullable = true, length = 150)
	public String getObs() { return this.obs; }
	public void setObs(String newObs) { this.obs = newObs; }

	@Lob
	public byte[] getFotoBD() { return fotoBD; }
	public void setFotoBD(byte[] fotoBD) { this.fotoBD = fotoBD; }

}