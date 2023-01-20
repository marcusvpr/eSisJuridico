package com.mpxds.mpbasic.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
import java.io.File;
//import java.text.NumberFormat;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.Barcode128;
//import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodeQRCode;
//import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class MpGenerateBarCode {

//	public static void main(String[] args) {
//
//		String pdfFilename = "d:/temp/MpSampleCodes.pdf";
//		//
//		MpGenerateBarCode generateInvoice = new MpGenerateBarCode();
//		generateInvoice.createPDF(pdfFilename, "db331653-aa93-4698-8b68-0341f8935076");
//	}

	public static void createPDF(String pdfFilename, String numCerp) {
		//
		Document doc = new Document();
		PdfWriter docWriter = null;

		try {
			docWriter = PdfWriter.getInstance(doc, new FileOutputStream(pdfFilename));
			doc.addAuthor("betterThanZero");
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator("www.mpxds.com");
			doc.addTitle("MPXDS - BarCode and QRCode");
			doc.setPageSize(PageSize.LETTER);

			doc.open();
//			PdfContentByte cb = docWriter.getDirectContent();

			String myString = "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" + numCerp;

//			Barcode128 code128 = new Barcode128();
//			code128.setCode(myString.trim());
//			code128.setCodeType(Barcode128.CODE128);
//			Image code128Image = code128.createImageWithBarcode(cb, null, null);
//			code128Image.setAbsolutePosition(10, 700);
//			code128Image.scalePercent(125);
//			doc.add(code128Image);
//
//			code128.setCodeType(Barcode128.CODE128_UCC);
//			code128Image = code128.createImageWithBarcode(cb, null, null);
//			code128Image.setAbsolutePosition(10, 650);
//			code128Image.scalePercent(125);
//			doc.add(code128Image);
//
//			BarcodeEAN codeEAN = new BarcodeEAN();
//			codeEAN.setCode(myString.trim());
//			codeEAN.setCodeType(BarcodeEAN.EAN13);
//			Image codeEANImage = code128.createImageWithBarcode(cb, null, null);
//			codeEANImage.setAbsolutePosition(10, 600);
//			codeEANImage.scalePercent(125);
//			doc.add(codeEANImage);

			BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);
			Image qrcodeImage = qrcode.getImage();
			qrcodeImage.setAbsolutePosition(10, 500);
			qrcodeImage.scalePercent(200);
			doc.add(qrcodeImage);

		} catch (DocumentException dex) {
			dex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (doc != null) {
				doc.close();
			}
			if (docWriter != null) {
				docWriter.close();
			}
		}
	}

	public static void atualizaPDF(String pathX, String arquivoX, String numCerp, Integer posX, Integer posY) {
		//
		System.out.println("MpGenerateBarCode.atualizaPDF() - 000 (PathX =  " + pathX + " ( ArqX.= " + arquivoX + 
																						" (Num.Cerp = " + numCerp);
		//
		PdfReader pdfReader = null;
		PdfStamper pdfStamper = null;
		
		try {
			//
			pdfReader = new PdfReader(pathX + arquivoX);

			pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(pathX + "out_" + arquivoX));

			PdfContentByte content = pdfStamper.getUnderContent(1); // 1 for the first page ...
			//
			String myString = "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" + numCerp;

			BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);
			Image qrcodeImage = qrcode.getImage();
			qrcodeImage.setAbsolutePosition(posX, posY);
			qrcodeImage.scalePercent(170);
			//
			content.addImage(qrcodeImage);

//			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ITALIC, BaseFont.CP1250, BaseFont.EMBEDDED);
//			content.beginText();
//			content.setFontAndSize(bf, 18);
//			content.showTextAligned(PdfContentByte.ALIGN_CENTER, "JavaCodeGeeks", 250, 650, 0);
//			content.endText();

			pdfStamper.close();
			pdfReader.close();
			//
			System.out.println("MpGenerateBarCode.atualizaPDF() - 001 (PathX =  " + pathX + " ( ArqX.= " + arquivoX);

		} catch (DocumentException dex) {
			dex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void createJPEG(String pdfFilename, String numCerp) {
		//
		String myString = "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" + numCerp;

		BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);
		Image qrcodeImage;
		try {
			qrcodeImage = qrcode.getImage();
			qrcodeImage.setAbsolutePosition(10, 500);
			qrcodeImage.scalePercent(200);

			java.awt.Image awtImage = qrcode.createAwtImage(Color.BLACK, Color.WHITE);

			BufferedImage bImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bImage.createGraphics();
			g.drawImage(awtImage, 0, 0, null);
			g.dispose();

			File outputfile = new File(pdfFilename);
			ImageIO.write(bImage, "jpg", outputfile);	
			//
		} catch (BadElementException e) {
			e.printStackTrace();
		}	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}