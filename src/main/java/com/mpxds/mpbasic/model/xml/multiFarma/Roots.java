package com.mpxds.mpbasic.model.xml.multiFarma;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.mpxds.mpbasic.model.xml.multiFarma.Root;

@XmlRootElement(name="roots")
public class Roots {
	//
    private List<Root> roots;

	// ---
	
    public List<Root> getRoots() { return roots; }
    public void setRoots (List<Root> roots) { this.roots = roots; }

}
