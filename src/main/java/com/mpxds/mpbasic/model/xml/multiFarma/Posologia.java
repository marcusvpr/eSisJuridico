package com.mpxds.mpbasic.model.xml.multiFarma;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="posologia")
public class Posologia {
	//
    private String id;    
    private String thumb;
    
//    private Produto[] produto;
    
    private List<Produto> produto = new ArrayList<Produto>();

	// ---
	
	@XmlElement(name="id")
    public String getId() { return id; }
    public void setId (String id) { this.id = id; }

	@XmlElement(name="thumb")
    public String getThumb() { return thumb; }
    public void setThumb (String thumb) { this.thumb = thumb; }

//    public Produto[] getProduto() { return produto; }
//    public void setProduto (Produto[] produto) { this.produto = produto; }

    public List<Produto> getProduto() { return produto; }
    public void setProduto (List<Produto> produto) { this.produto = produto; }

}
