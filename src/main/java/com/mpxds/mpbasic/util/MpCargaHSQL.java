package com.mpxds.mpbasic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.web.context.ContextLoader;

import com.mpxds.mpbasic.model.MpGrupo;
import com.mpxds.mpbasic.model.MpObjeto;
import com.mpxds.mpbasic.model.MpUsuario;
import com.mpxds.mpbasic.model.enums.MpGrupamentoMenu;
import com.mpxds.mpbasic.model.enums.MpGrupoMenu;
import com.mpxds.mpbasic.model.enums.MpMenuGlobalObjeto;
import com.mpxds.mpbasic.model.enums.MpStatusObjeto;
import com.mpxds.mpbasic.model.enums.MpTipoObjeto;
import com.mpxds.mpbasic.repository.MpGrupos;
import com.mpxds.mpbasic.repository.MpObjetos;
import com.mpxds.mpbasic.service.MpGrupoService;
import com.mpxds.mpbasic.service.MpObjetoService;
import com.mpxds.mpbasic.util.jsf.MpFacesUtil;

public class MpCargaHSQL {
	//
	
	@Inject
	private static MpObjetos mpObjetos;
	@Inject
	private static MpObjetoService mpObjetoService;
	@Inject
	private static MpGrupos mpGrupos;
	@Inject
	private static MpGrupoService mpGrupoService;
	
	public static void main(String[] args) {
		//
		
//		MpObjetos mpObjetos = new MpObjetos();
//		MpObjetos mpObjetos = MpObjetos.getInjector(getContext()).getInstance(MpObjetos.class);
		
		MpObjetos mpObjetos =(MpObjetos)ContextLoader.getCurrentWebApplicationContext().getBean(MpObjetos.class);
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MpProtestoPU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

//		System.out.println("Starting Transaction");
//		entityManager.getTransaction().begin();
//		MpGrupo MpGrupo = new MpGrupo();
//		MpGrupo.setName("Pankaj");
//		System.out.println("Saving MpGrupo to Database");
//
//		entityManager.persist(MpGrupo);
//		entityManager.getTransaction().commit();
//		System.out.println("Generated MpGrupo ID = " + MpGrupo.getMpGrupoId());

		// get an object using primary key.
//		MpGrupo emp = entityManager.find(MpGrupo.class, 1L);
//		System.out.println("got object " + emp.getNome() + " " + emp.getId());

//		// get all the objects from MpGrupo table
//		@SuppressWarnings("unchecked")
//		List<MpGrupo> listMpGrupo = entityManager.createQuery("SELECT e FROM MpGrupo e").getResultList();
//
//		if (listMpGrupo == null) {
//			System.out.println("No MpGrupo found . ");
//		} else {
//			for (MpGrupo empl : listMpGrupo) {
//				System.out.println("MpGrupo name= " + empl.getNome() + ", MpGrupo id " + empl.getId());
//			}
//		}
//		
//		// get all the objects from MpUsuario table
//		@SuppressWarnings("unchecked")
//		List<MpUsuario> listMpUsuario = entityManager.createQuery("SELECT e FROM MpUsuario e").getResultList();
//
//		if (listMpUsuario == null) {
//			System.out.println("No MpUsuario found . ");
//		} else {
//			for (MpUsuario empl : listMpUsuario) {
//				System.out.println("MpUsuario name= " + empl.getNome() + ", MpUsuario id " + empl.getId());
//			}
//		}

//		// remove and entity
//		entityManager.getTransaction().begin();
//		System.out.println("Deleting MpGrupo with ID = " + emp.getMpGrupoId());
//		entityManager.remove(emp);
//		entityManager.getTransaction().commit();
		
    	// Trata Objetos...
		List<MpObjeto> mpObjetoList = mpObjetos.mpObjetoList("0");
		//
		System.out.println("MpLoginBean.login() - 003 ( " + mpObjetoList.size());

		if (mpObjetoList.size() == 0) { // Gerar Cadastro Objetos !
			//
			geraCadastroObjetos();
		}
		
		

		// close the entity manager
		entityManager.close();
		entityManagerFactory.close();

	}
	
	public static void geraCadastroObjetos() {
		//
		MpGrupo mpGrupoAdm = mpGrupos.porNome("ADMINISTRADORES");
		if (null == mpGrupoAdm) {
			//
			MpFacesUtil.addInfoMessage(
								"Grupo ADMINISTRADORES... não existe! Contactar o SUPORTE!");
			return;
		}
		MpGrupo mpGrupoProtestoAdm = mpGrupos.porNome("PROTESTOS_ADMIN");
		if (null == mpGrupoProtestoAdm) {
			//
			MpFacesUtil.addInfoMessage("Grupo Protesto ADMIN. não existe! Contactar o SUPORTE!");
			return;
		}
		MpGrupo mpGrupoProtesto = mpGrupos.porNome("PROTESTOS");
		if (null == mpGrupoProtesto) {
			//
			MpFacesUtil.addInfoMessage("Grupo Protesto não existe! Contactar o SUPORTE!");
			return;
		}
		//
		List<MpObjeto> mpObjetoList = new ArrayList<MpObjeto>();
		List<MpObjeto> mpObjetoProtestoAdmList = new ArrayList<MpObjeto>();
		List<MpObjeto> mpObjetoProtestoList = new ArrayList<MpObjeto>();
		//
        List<MpMenuGlobalObjeto> mpMenuGlobalObjetoList = Arrays.
        												asList(MpMenuGlobalObjeto.values());
		//
//		System.out.println("MpLoginBean.geraCadastroObjetos() - 000 ( " +
//													        mpMenuGlobalObjetoList.size());
        
    	Iterator<MpMenuGlobalObjeto> itrO = mpMenuGlobalObjetoList.iterator(); 
    	//
    	while(itrO.hasNext()) {
	    	//
    		MpMenuGlobalObjeto mpMenuGlobalObjeto = (MpMenuGlobalObjeto) itrO.next();
    		
    		MpObjeto mpObjeto = new MpObjeto();
    		
    		mpObjeto.setTransacao(mpMenuGlobalObjeto.getTransacao());
    		mpObjeto.setCodigo(mpMenuGlobalObjeto.getCodigo());
    		mpObjeto.setNome(mpMenuGlobalObjeto.getNome());
    		mpObjeto.setDescricao(mpMenuGlobalObjeto.getDescricao());
    		mpObjeto.setIcon(mpMenuGlobalObjeto.getIcon());
    		mpObjeto.setIndSeparator(mpMenuGlobalObjeto.getIndSeparator());
    		mpObjeto.setMpStatusObjeto(MpStatusObjeto.valueOf(mpMenuGlobalObjeto.
    																	getMpStatusObjeto()));
    		mpObjeto.setMpGrupoMenu(MpGrupoMenu.valueOf(mpMenuGlobalObjeto.getMpGrupoMenu()));
    		mpObjeto.setMpGrupamentoMenu(MpGrupamentoMenu.valueOf(mpMenuGlobalObjeto.
    																	getMpGrupamentoMenu()));
    		mpObjeto.setMpTipoObjeto(MpTipoObjeto.valueOf(mpMenuGlobalObjeto.getMpTipoObjeto()));
    		mpObjeto.setIndResponsive(mpMenuGlobalObjeto.getIndResponsive());
    		
    		mpObjeto = mpObjetoService.salvar(mpObjeto);
    		//
    		mpObjetoList.add(mpObjeto);
    		//
    		if (mpMenuGlobalObjeto.getPerfil_0().equals("PROTESTOS_ADMIN"))
    			mpObjetoProtestoAdmList.add(mpObjeto);
    		if (mpMenuGlobalObjeto.getPerfil_1().equals("PROTESTOS"))
    			mpObjetoProtestoList.add(mpObjeto);
    		//
    	}
    	//
		mpGrupoAdm.setMpObjetos(mpObjetoList);
		
		mpGrupoAdm = mpGrupoService.salvar(mpGrupoAdm);
		//
		if (mpObjetoProtestoAdmList.size() > 0) {
			mpGrupoProtestoAdm.setMpObjetos(mpObjetoProtestoAdmList);
		
			mpGrupoProtestoAdm = mpGrupoService.salvar(mpGrupoProtestoAdm);
		}
		if (mpObjetoProtestoList.size() > 0) {
			mpGrupoProtesto.setMpObjetos(mpObjetoProtestoList);
		
			mpGrupoProtesto = mpGrupoService.salvar(mpGrupoProtesto);
		}
		
		//
//		System.out.println("MpLoginBean.geraCadastroObjetos() - 001 ( " + mpObjetoList.size());
	}
	
	
	
}
