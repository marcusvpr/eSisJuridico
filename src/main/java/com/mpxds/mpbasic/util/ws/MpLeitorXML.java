package com.mpxds.mpbasic.util.ws;

import java.io.InputStream;
import java.util.List;

import com.mpxds.mpbasic.model.xml.MpNegociacaoXML;
import com.mpxds.mpbasic.model.xml.multiFarma.MpMultiFarmaXML;
import com.mpxds.mpbasic.model.xml.multiFarma.Root;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MpLeitorXML {

	@SuppressWarnings("unchecked")
	public List<MpNegociacaoXML> carregaNegociacaoXML(InputStream inputStream) {
		XStream stream = new XStream(new DomDriver());

		stream.alias("negociacao", MpNegociacaoXML.class);

		return (List<MpNegociacaoXML>) stream.fromXML(inputStream);
	}
	
	@SuppressWarnings("unchecked")
	public List<MpMultiFarmaXML> carregaMultiFarmaXML(InputStream inputStream) {
		XStream stream = new XStream(new DomDriver());

		stream.alias("mpMultiFarmaXML", MpMultiFarmaXML.class);

		return (List<MpMultiFarmaXML>) stream.fromXML(inputStream);
	}
	
	@SuppressWarnings("unchecked")
	public List<Root> carregaMultiFarmaRootXML(InputStream inputStream) {
		XStream stream = new XStream(new DomDriver());

		stream.alias("root", Root.class);

		return (List<Root>) stream.fromXML(inputStream);
	}

}