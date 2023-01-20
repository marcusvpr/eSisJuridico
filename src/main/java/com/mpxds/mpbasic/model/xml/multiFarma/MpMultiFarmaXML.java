package com.mpxds.mpbasic.model.xml.multiFarma;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class MpMultiFarmaXML {
	//
	private Root root;

	// ---
	public MpMultiFarmaXML() {
		super();
	}
	
	public MpMultiFarmaXML(Root root) {
		super();
		//
		this.root = root;
	}

	public Root getRoot() { return root; }
	public void setRoot(Root root) { this.root = root;	}
	
}