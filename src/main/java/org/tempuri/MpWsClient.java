package org.tempuri;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;
import org.datacontract.schemas._2004._07.ecartorio_service.Pedido;

import com.mpxds.mpbasic.util.MpAppUtil;

public class MpWsClient {
	//    
	public static void main(String[] args) throws JAXBException, IOException {
		//
		System.out.println("MpECartorioRJBean.executaWS() - 000");

        org.tempuri.Service serviceX = new org.tempuri.Service();
        
        MpHeaderHandlerResolver mpHandlerResolver = new MpHeaderHandlerResolver();
        
        serviceX.setHandlerResolver(mpHandlerResolver);
        
        org.tempuri.IService serviceInterfaceX = serviceX.getPort(org.tempuri.IService.class,
        																new javax.xml.ws.soap.AddressingFeature());
         
		ArrayOfPedido arrayOfPedido = serviceInterfaceX.listarPedidos("0753", "", "", "2016/01/01", "2016/12/31", "",
																							"", "", "", "", "", "", "");
		//
		JAXBContext jaxbContext = JAXBContext.newInstance(Pedido.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// Marshal the employees list in console
		OutputStream outputStreamX = new FileOutputStream("arrayOfPedido.txt");
		
		jaxbMarshaller.marshal(arrayOfPedido, outputStreamX);

		InputStream inputStreamX = new FileInputStream("arrayOfPedido.txt");
		Charset charsetX = StandardCharsets.UTF_8;
		
//		jaxbMarshaller.marshal(arrayOfPedido, System.out);

		// //Marshal the employees list in file
		// jaxbMarshaller.marshal(arrayOfPedido, new
		// File("c:/temp/employees.xml"));
		//
		String respostaWS = MpAppUtil.convertIS(inputStreamX, charsetX);
		
		System.out.println("MpWsClient - 002 (Pedido RespostaWS = " + respostaWS);
	}
	
}
