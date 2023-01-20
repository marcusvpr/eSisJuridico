package org.tempuri;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class MpHeaderHandlerResolver implements HandlerResolver {
	//
	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		//
		List<Handler> handlerChain = new ArrayList<Handler>();

		MpHeaderHandler hh = new MpHeaderHandler();

		handlerChain.add(hh);
		//
		return handlerChain;
	}
}