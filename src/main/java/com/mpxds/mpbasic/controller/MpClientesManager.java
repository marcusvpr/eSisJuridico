package com.mpxds.mpbasic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpCliente;
import com.mpxds.mpbasic.model.MpEndereco;
import com.mpxds.mpbasic.model.enums.MpEstadoCivil;
import com.mpxds.mpbasic.model.enums.MpEstadoUF;
import com.mpxds.mpbasic.model.enums.MpSexo;
import com.mpxds.mpbasic.model.enums.MpStatus;
import com.mpxds.mpbasic.model.enums.MpTipoPessoa;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.repository.MpEnderecos;
import com.mpxds.mpbasic.service.MpClienteService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;
import com.mpxds.mpbasic.util.ws.MpClienteWebService;

//@ManagedBean
//@SessionScoped
@Named
@ViewScoped
public class MpClientesManager implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Inject
	private MpClienteService mpClienteService;

	@Inject
	private MpEnderecos mpEnderecos;
	
	private MpCliente mpClienteEdicao;
	
	private MpTipoPessoa mpTipoPessoa;
	private List<MpTipoPessoa> mpTipoPessoaList;
	
	private MpStatus mpStatus;
	private List<MpStatus> mpStatusList;
	
	private MpSexo mpSexo;
	private List<MpSexo> mpSexoList;
	
	private MpEstadoCivil mpEstadoCivil;
	private List<MpEstadoCivil> mpEstadoCivilList;
	
	private MpEstadoUF mpEstadoUF;
	private List<MpEstadoUF> mpEstadoUFList;
	
	private MpEndereco mpEnderecoEdicao;
	private List<MpEndereco> mpEnderecoExcluidoList = new ArrayList<MpEndereco>();;
		
	private String modoClienteTela;
	private String modoEnderecoDialog;

	// ---

	public void inicializar() {
		//
		if (null == this.mpClienteEdicao)
			this.mpClienteEdicao = new MpCliente();
		else
			this.modoClienteTela = "Edição";
		//
		this.mpEnderecoEdicao = new MpEndereco();
		
		this.mpTipoPessoaList = Arrays.asList(MpTipoPessoa.values());
		this.mpStatusList = Arrays.asList(MpStatus.values());
		this.mpSexoList = Arrays.asList(MpSexo.values());
		this.mpEstadoCivilList = Arrays.asList(MpEstadoCivil.values());
		//
		this.mpEstadoUFList = Arrays.asList(MpEstadoUF.values());
	}
		
	public String novoMpCliente() {
		//
		this.modoClienteTela = "Novo";

		this.mpClienteEdicao = new MpCliente();
		
		this.mpEnderecoExcluidoList.clear();
		//
		return "MpCadastroCliente?faces-redirect=true";
	}
	
	public void editaMpCliente() {
		//
		this.modoClienteTela = "Edição";
	}
	
	public void novoMpEndereco() {
		//
		this.modoEnderecoDialog = "Novo";
		
		this.mpEnderecoEdicao = new MpEndereco();
		
		this.mpEnderecoEdicao.setMpCliente(mpClienteEdicao);
	}
	
	public void editaMpEndereco() {
		//
		this.modoEnderecoDialog = "Edição";
	}
	
	public void excluirEndereco() {
		//
		try {
			this.mpClienteEdicao.getMpEnderecos().remove(this.mpEnderecoEdicao);
			
			this.mpEnderecoExcluidoList.add(this.mpEnderecoEdicao);
			
			MpFacesUtil.addInfoMessage("Endereco " + this.mpEnderecoEdicao.getLogradouro()
																	+ " excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void salvar() {
		//
		this.mpClienteService.salvar(this.mpClienteEdicao);

		if (this.mpEnderecoExcluidoList.size() > 0) {
			for (MpEndereco mpEndereco: this.mpEnderecoExcluidoList ) {
				//
				this.mpEnderecos.remover(mpEndereco);
			}
		}
		//
		this.mpClienteEdicao = new MpCliente();

		this.mpEnderecoExcluidoList.clear();
		
		MpFacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}

	public void onCepWebService() {
    	//
		MpCepXML mpCepXML = MpClienteWebService.executaCep(this.getMpEnderecoEdicao().getCep());
		if (null == mpCepXML)
			MpFacesUtil.addErrorMessage("CEP WebService... sem retorno !");
		else {
//			System.out.println("MpCadastroClienteBean.onCepWebService() - ( " +
//				this.mpCliente.getMpEnderecoEntrega().getCep() + " / " + mpCepXML.getLogradouro());
			//
			this.getMpEnderecoEdicao().setLogradouro(mpCepXML.getLogradouro());
			this.getMpEnderecoEdicao().setComplemento(mpCepXML.getComplemento());
			this.getMpEnderecoEdicao().setBairro(mpCepXML.getBairro());
			this.getMpEnderecoEdicao().setCidade(mpCepXML.getCidade());
			// Trata UF! ...
			MpEstadoUF mpEstadoUF = MpEstadoUF.XX;
			if (null == mpCepXML.getEstado())
				mpEstadoUF = MpEstadoUF.XX;
			else {
				mpEstadoUF = MpEstadoUF.valueOf(mpCepXML.getEstado().toUpperCase());
				if (null == mpEstadoUF)
					mpEstadoUF = MpEstadoUF.XX;
			}
			this.getMpEnderecoEdicao().setMpEstadoUF(mpEstadoUF);
			//
		}
    }

	// ---
	
	public MpCliente getMpClienteEdicao() { return mpClienteEdicao; }
	public void setMpClienteEdicao(MpCliente mpClienteEdicao) { 
														this.mpClienteEdicao = mpClienteEdicao; }

	public MpEndereco getMpEnderecoEdicao() { return mpEnderecoEdicao; 	}
	public void setMpEnderecoEdicao(MpEndereco mpEnderecoEdicao) {
														this.mpEnderecoEdicao = mpEnderecoEdicao; }
	
	public MpTipoPessoa getMpTipoPessoa() {	return mpTipoPessoa; }
	public void setMpTipoPessoa(MpTipoPessoa mpTipoPessoa) { this.mpTipoPessoa = mpTipoPessoa; }
	public List<MpTipoPessoa> getMpTipoPessoaList() { return mpTipoPessoaList; }

	public MpStatus getMpStatus() {	return mpStatus; }
	public void setMpStatus(MpStatus mpStatus) { this.mpStatus = mpStatus; }
	public List<MpStatus> getMpStatusList() { return mpStatusList; }

	public MpSexo getMpSexo() { return mpSexo; }
	public void setMpSexo(MpSexo mpSexo) { this.mpSexo = mpSexo; }
	public List<MpSexo> getMpSexoList() { return mpSexoList; }

	public MpEstadoCivil getMpEstadoCivil() { return mpEstadoCivil;	}
	public void setMpEstadoCivil(MpEstadoCivil mpEstadoCivil) {	this.mpEstadoCivil = mpEstadoCivil; }
	public List<MpEstadoCivil> getMpEstadoCivilList() {	return mpEstadoCivilList; }

	public MpEstadoUF getMPEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }
	public List<MpEstadoUF> getMpEstadoUFList() { return mpEstadoUFList; }

	public String getModoClienteTela() { return modoClienteTela; }
	public void setModoClienteTela(String modoClienteTela) { this.modoClienteTela = modoClienteTela; }

	public String getModoEnderecoDialog() { return modoEnderecoDialog; }
	public void setModoEnderecoDialog(String modoEnderecoDialog) { 
													this.modoEnderecoDialog = modoEnderecoDialog; }
	
}