package com.mpxds.site.mvvm;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
//
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
//
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GeraCRUDVM {
	//
	@Wire("#winGeraCRUDVM")
	private Window winGeraCRUDVM;
	@Wire
    private boolean makeAsReadOnly;
	//
    @Wire
	private Set<String> artefatoSelecaoList = new HashSet<String>(0);
    @Wire
    private String artefatoSels = "";
    @Wire
    private Boolean indSelecao = false;
    //
    @Wire
    private String origem = "dolar";
    private String Origem = "Dolar";
    @Wire
    private String destino = "objetoAcao";
    private String Destino = "ObjetoAcao";
	//
	// private File file = new File("C:/teste.txt");
	private File fileOrigem;
	private File fileDestino;
	//
    private Scanner scan; 
    private String line;
    //
	private String path = Sessions.getCurrent().getWebApp().getServletContext().getRealPath("/");
	//
	
    // ========================================================
    
    public boolean getMakeAsReadOnly() {
    	return this.makeAsReadOnly;
    }
    public void setMakeAsReadOnly(boolean newMakeAsReadOnly) {
    	this.makeAsReadOnly = newMakeAsReadOnly;
    }

    public void setArtefatoSels(String newArtefatoSels) {
        this.artefatoSels = newArtefatoSels;
    }
    public String getArtefatoSels() {
        return this.artefatoSels;
    }
	
    public void setIndSelecao(Boolean newIndSelecao) {
        this.indSelecao = newIndSelecao;
    }
    public Boolean getIndSelecao() {
        return this.indSelecao;
    }
	
	public String getOrigem() {
		return this.origem;
	}
    public void setOrigem(String newOrigem) {
    	this.origem = newOrigem;
    }

    public String getDestino() {
		return this.destino;
	}
    public void setDestino(String newDestino) {
    	this.destino = newDestino;
    }

    // -------------------------------- Inicio ------------------------------------
    @NotifyChange({"*"})
    @Init
	public void Inicio() throws IllegalAccessException, InstantiationException {
    	//
    	this.makeAsReadOnly = false;
    	//
    	// System.out.println("\nApp Deployed Directory path: " +
    	//                              Sessions.getCurrent().getWebApp().getServletContext().getRealPath("/"));
    	// System.out.println("getContextPath(): " + 
    	//                              Sessions.getCurrent().getWebApp().getServletContext().getContextPath());
    }
    
    @NotifyChange("*")
    @Command
	public void onCheckArtefato (@BindingParam("artefato") String artefato,
								 @BindingParam("state") boolean state) {
		//
    	if (state)
    	  artefatoSelecaoList.add(artefato);  
    	else
      	  artefatoSelecaoList.remove(artefato);  
    	//
    	artefatoSels = "";
    	//
    	Iterator<String> itr = artefatoSelecaoList.iterator();  
	    while(itr.hasNext()) {
	         String artefatoList = (String) itr.next();  
	         //
	     	artefatoSels = artefatoSels + " [" + artefatoList + "]";
	    }
    }
    	
    @NotifyChange("*")
    @Command
    public void executaGeracao() throws ParseException {
    	//
    	if (this.origem.isEmpty() || this.origem.trim().equals("")) {
     	   Messagebox.show("Informar ORIGEM !"); 
     	   return;
     	}
    	if (this.destino.isEmpty() || this.destino.trim().equals("")) {
      	   Messagebox.show("Informar Destino !"); 
      	   return;
      	}
    	//
    	if (artefatoSelecaoList.size()==0) {
    	   Messagebox.show("Não existe(m) Artefato(s) Selecionado(s) !");
    	   return;
    	}
    	//
	    // Gera Padrão nome de Variavel (Ex.: dolar -> Dolar) ...
    	//
    	this.Origem  = this.origem.substring(0,1).toUpperCase()  + this.origem.substring(1);
    	this.Destino = this.destino.substring(0,1).toUpperCase() + this.destino.substring(1);
    	//
    	this.indSelecao = true;
    	this.makeAsReadOnly = true;
    	//
    	setIndSelecao(indSelecao); 
        BindUtils.postNotifyChange(null,null,this,"indSelecao");
        //
        artefatoSels = "";
        //
	    Iterator<String> itr = artefatoSelecaoList.iterator();
	    while(itr.hasNext()) {
	         String artefatoSelecao = (String) itr.next();
	         //
	         artefatoSels = artefatoSels + "[" + artefatoSelecao + "]\n";
	         //
	         trataSelecao(artefatoSelecao);
	    }
	}
    
    public void trataSelecao(String selecao) {
    	//
    	if (selecao.equals("Repository")) geraRepository();
    	if (selecao.equals("Model")) geraModel();
    	if (selecao.equals("DataFilter")) geraDataFilter();
    	if (selecao.equals("MVVM")) geraMVVM();
    	if (selecao.equals("Service")) geraService();
    	if (selecao.equals("ServiceImpl")) geraServiceImpl();
    	if (selecao.equals("WebApp")) geraWebApp();
    }
    
    public void geraRepository() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "Repository.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "Repository.java");
    	//
    	this.gravaCRUDVM();
//        //
//		try (FileInputStream fis = new FileInputStream(file)) {
//			//
//			System.out.println("Total file size to read (in bytes) : "+ fis.available());
//			//
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//	        //
//	        StringBuilder out = new StringBuilder();
//	        String line;
//	        //
//	        while ((line = reader.readLine()) != null) {
//	            out.append(line);
//	        }
//	        System.out.println(out.toString());   //Prints the string content read from input stream
//	        //
//	        reader.close();
//	    } catch (IOException e) {
//			e.printStackTrace();
//		}
	}
    
    public void geraModel() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + ".java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + ".java");
    	//
    	this.gravaCRUDVM();
    }
    
    public void geraDataFilter() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "DataFilter.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "DataFilter.java");
    	//
    	this.gravaCRUDVM();
    }
    
    public void geraMVVM() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "ListVM.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "ListVM.java");
    	//
    	this.gravaCRUDVM();
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "CRUDVM.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "CRUDVM.java");
    	//
    	this.gravaCRUDVM();
    }
    
    public void geraService() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "Service.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "Service.java");
    	//
    	this.gravaCRUDVM();
    }
    
    public void geraServiceImpl() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.Origem.trim() + "ServiceJpaImpl.java");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.Destino.trim() + "ServiceJpaImpl.java");
    	//
    	this.gravaCRUDVM();
    }
    
    public void geraWebApp() {
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.origem.trim() + "_list.zul");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.destino.trim() + "_list.zul");
    	//
    	this.gravaCRUDVM();
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\" + this.origem.trim() + "CRUD.zul");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\" + this.destino.trim() + "CRUD.zul");
    	//
    	this.gravaCRUDVM();
    	//
    	this.fileOrigem  = new File(this.path + "docs\\crudVM\\index-" + this.origem.trim() + "_list.zul");
    	this.fileDestino = new File(this.path + "docs\\crudVM\\index-" + this.destino.trim() + "_list.zul");
    	//
    	this.gravaCRUDVM();
    }

    public void gravaCRUDVM() {
    	//
    	this.line = "";
    	//
		try { 
			FileInputStream fis = new FileInputStream(fileOrigem);
			//
			FileWriter fw = new FileWriter(this.fileDestino);
			//
	        this.scan = new java.util.Scanner(fis, "UTF-8").useDelimiter("\\A");
			// iterate through the file line by line
			while(scan.hasNextLine()){
				// print the token
				this.line = scan.next();
				//
				if (this.line.indexOf(origem) > 0) this.line = this.line.replace(origem, destino);
				if (this.line.indexOf(Origem) > 0) this.line = this.line.replace(Origem, Destino);
				//
	            fw.write(this.line);
				// System.out.println(this.line);
	            //
			}
	        this.scan.close();
	        //
	        fw.close();
	        fis.close();
	        //
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
