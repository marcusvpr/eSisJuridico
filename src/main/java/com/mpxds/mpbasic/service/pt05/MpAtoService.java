package com.mpxds.mpbasic.service.pt05;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import com.mpxds.mpbasic.exception.MpNegocioException;
import com.mpxds.mpbasic.model.MpAuditoriaObjeto;
import com.mpxds.mpbasic.model.pt05.MpAto;
import com.mpxds.mpbasic.model.pt05.MpAtoComposicao;
import com.mpxds.mpbasic.repository.pt05.MpAtos;
import com.mpxds.mpbasic.security.MpSeguranca;
import com.mpxds.mpbasic.util.jpa.MpTransactional;

public class MpAtoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MpAtos mpAtos;

	@Inject
	private MpSeguranca mpSeguranca;

	// -------------
	
	@MpTransactional
	public MpAto salvar(MpAto mpAto) throws MpNegocioException {
		//
		if (null == mpAto.getId()) {
			//
			MpAto mpCodigoExistente = mpAtos.porCodigoSequencia(mpAto.getCodigo(), mpAto.getSequencia());
			
			if (mpCodigoExistente != null && !mpCodigoExistente.equals(mpAtos)) {
				throw new MpNegocioException("Já existe um ATO/SEQUÊNCIA... com os CÓDIGOs informados.");
			}
		}

		// INICIO - Trata dados auditoria ! -----------------------------------
		MpAuditoriaObjeto mpAuditoriaObjeto;
		if (null == mpAto.getId()) { 
			mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrInc(new Date());
			mpAuditoriaObjeto.setUserInc(mpSeguranca.getLoginUsuario());
		} else {
			mpAuditoriaObjeto = mpAto.getMpAuditoriaObjeto();
			if (null == mpAuditoriaObjeto) mpAuditoriaObjeto = new MpAuditoriaObjeto();
			mpAuditoriaObjeto.setDtHrAlt(new Date());
			mpAuditoriaObjeto.setUserAlt(mpSeguranca.getLoginUsuario());				
		}
		mpAto.setMpAuditoriaObjeto(mpAuditoriaObjeto);
		// FIM - Trata dados auditoria ! -----------------------------------
				
		return mpAtos.guardar(mpAto);
	}
	

	public MpAto tratarValorTotal(MpAto mpAto, Integer scOficVariavel, Integer scOficLei3217,
								 			   Integer scOficLei4664,  Integer scOficLei111,
								 			   Integer scOficLei6281) {
		//
		for (MpAtoComposicao mpAtoComposicaoX : mpAto.getMpAtoComposicaos()) {
			//
//			System.out.println("MpAtoService.tratarValorTotal() - ( mpAto.id = " + mpAto.getId() + " / " +
//											mpAtoComposicaoX.getMpCustasComposicao().getTabela());
			
			if (null == mpAtoComposicaoX.getMpCustasComposicao()) continue;
					
			if  (null == mpAtoComposicaoX.getMpCustasComposicao().getValorCusta())
				mpAtoComposicaoX.getMpCustasComposicao().setValorCusta(BigDecimal.ZERO);
			//
			if (mpAtoComposicaoX.getMpCustasComposicao().getTabela().equals("0024"))
				mpAto.getMpValorAto().setValorAtoEmolumento(mpAto.getMpValorAto().getValorAtoEmolumento().
									add(mpAtoComposicaoX.getMpCustasComposicao().getValorCusta()));

			if (mpAtoComposicaoX.getMpCustasComposicao().getTabela().equals("0051"))
				mpAto.getMpValorAto().setValorAtoLei3761(mpAto.getMpValorAto().getValorAtoLei3761().
									add(mpAtoComposicaoX.getMpCustasComposicao().getValorCusta()));

			if (mpAtoComposicaoX.getMpCustasComposicao().getTabela().equals("0052"))
				mpAto.getMpValorAto().setValorAtoLei590(mpAto.getMpValorAto().getValorAtoLei590().
									add(mpAtoComposicaoX.getMpCustasComposicao().getValorCusta()));

			if (mpAtoComposicaoX.getMpCustasComposicao().getTabela().equals("0062"))
				mpAto.getMpValorAto().setValorAtoLei6281(mpAto.getMpValorAto().getValorAtoLei6281().
									add(mpAtoComposicaoX.getMpCustasComposicao().getValorCusta()));

			if (mpAtoComposicaoX.getMpCustasComposicao().getTabela().equals("0063"))
				mpAto.getMpValorAto().setValorAtoVariavel(mpAto.getMpValorAto().getValorAtoVariavel().
									add(mpAtoComposicaoX.getMpCustasComposicao().getValorCusta()));
			//
		}
		//
		mpAto.getMpValorAto().setValorAtoVariavel(mpAto.getMpValorAto().getValorAtoEmolumento().
					multiply(new java.math.BigDecimal(String.valueOf(scOficVariavel))).
					divide(new java.math.BigDecimal(String.valueOf(100))).
					setScale(2, BigDecimal.ROUND_DOWN));
		
		mpAto.getMpValorAto().setValorAtoLei3217(mpAto.getMpValorAto().getValorAtoEmolumento().
					multiply(new java.math.BigDecimal(String.valueOf(scOficLei3217))).
					divide(new java.math.BigDecimal(String.valueOf(100))).
					setScale(2, BigDecimal.ROUND_DOWN));
		
		mpAto.getMpValorAto().setValorAtoLei4664(mpAto.getMpValorAto().getValorAtoEmolumento().
					multiply(new java.math.BigDecimal(String.valueOf(scOficLei4664))).
					divide(new java.math.BigDecimal(String.valueOf(100))).
					setScale(2, BigDecimal.ROUND_DOWN));
	
		mpAto.getMpValorAto().setValorAtoLei111(mpAto.getMpValorAto().getValorAtoEmolumento().
					multiply(new java.math.BigDecimal(String.valueOf(scOficLei111))).
					divide(new java.math.BigDecimal(String.valueOf(100))).
					setScale(2, BigDecimal.ROUND_DOWN));
		
		mpAto.getMpValorAto().setValorAtoLei6281(mpAto.getMpValorAto().getValorAtoEmolumento().
					multiply(new java.math.BigDecimal(String.valueOf(scOficLei6281))).
					divide(new java.math.BigDecimal(String.valueOf(100))).
					setScale(2, BigDecimal.ROUND_DOWN));
		//
		return mpAto;
	}
	
}
