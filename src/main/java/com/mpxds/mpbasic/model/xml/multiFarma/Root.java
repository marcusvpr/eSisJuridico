package com.mpxds.mpbasic.model.xml.multiFarma;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class Root {
	//
    private List<Posologia> posologia;

	// ---
	
    public List<Posologia> getPosologia() { return posologia; }
    public void setPosologia (List<Posologia> posologia) { this.posologia = posologia; }

}
