package com.mpxds.mpbasic.model.sisJuri;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.mpxds.mpbasic.model.MpArquivoBD;
import com.mpxds.mpbasic.model.MpBaseEntity;
import com.mpxds.mpbasic.model.MpEnderecoLocal;
import com.mpxds.mpbasic.model.enums.MpArquivoAcao;

@Entity
@Table(name = "MP_SJ_CLIENTE")
public class MpClienteSJ extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
    
	private String nome;
	private String cpfCnpj;
	private Boolean indParteContraria;
	private String email;
	private String telefone;
	private String observacao;
	private MpEnderecoLocal mpEnderecoLocal;

	private byte[] arquivoBD;

	private MpArquivoAcao mpArquivoAcao;
	private MpArquivoBD mpArquivoBD;
	
    private Long idCarga;

	// ---
	
	public MpClienteSJ() {
		super();
	}

	// --- 
	
	@NotBlank(message = "Por favor, informe o NOME")
	@Column(nullable = false, length = 100, unique = true)
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	@Column(name = "cpf_cnpj", nullable = false, length = 20)
	public String getCpfCnpj() { return cpfCnpj; }
	public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

	@Column(name = "ind_Parte_Contraria", nullable = true)
	public Boolean getIndParteContraria() { return indParteContraria; }
	public void setIndParteContraria(Boolean indParteContraria) { this.indParteContraria = indParteContraria; }

	@Column(nullable = true, length = 255)
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@Column(nullable = true, length = 50)
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	@Column(nullable = true, length = 255)
	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

	@Embedded
	public MpEnderecoLocal getMpEnderecoLocal() { return mpEnderecoLocal; }
	public void setMpEnderecoLocal(MpEnderecoLocal mpEnderecoLocal) { 
															this.mpEnderecoLocal = mpEnderecoLocal; }

	@Lob
	@Column(name = "mpArquivo_BD", columnDefinition = "blob", nullable = true, length = 10000)
	public byte[] getArquivoBD() { return arquivoBD; }
	public void setArquivoBD(byte[] arquivoBD) { this.arquivoBD = arquivoBD; }

	@Enumerated(EnumType.STRING)
	@Column(name = "mpArquivo_acao", nullable = true, length = 15)
	public MpArquivoAcao getMpArquivoAcao() { return mpArquivoAcao; }
	public void setMpArquivoAcao(MpArquivoAcao mpArquivoAcao) { this.mpArquivoAcao = mpArquivoAcao; }

	@ManyToOne
	@JoinColumn(name = "mpArquivo_BD_id", nullable = true)
	public MpArquivoBD getMpArquivoBD() { return mpArquivoBD; }
	public void setMpArquivoBD(MpArquivoBD mpArquivoBD) { this.mpArquivoBD = mpArquivoBD; }

	@Column(name = "id_carga", nullable = true)
	public Long getIdCarga() { return this.idCarga; }
	public void setIdCarga(Long idCarga) { this.idCarga = idCarga; }
	
}
