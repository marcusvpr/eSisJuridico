package com.mpxds.mpbasic.util; 

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.charset.Charset;

import javax.crypto.Mac;
//import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import java.security.GeneralSecurityException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.mpxds.mpbasic.repository.filter.MpAlarmeFilter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ---

public class MpAppUtil {
	//
	
	public static String criptografar(String senha){
		try{
			MessageDigest digs = MessageDigest.getInstance("SHA-256");
			digs.update((new String(senha)).getBytes("UTF8"));
			String senhaCript  = new String(digs.digest());
			return senhaCript;
		}catch (Exception e){
			System.out.println("Erro ao criptografar" + e);
			return "";
		}
	}	
    
	public static long difData(Date datFinal, Date datInicial) {
		//
        Calendar dataInicial = Calendar.getInstance();
        //
        dataInicial.setTime(datInicial);
        //
        Calendar dataFinal = Calendar.getInstance();
        //
        dataFinal.setTime(datFinal);
        // Calcula a diferen�a entre a data final e da data de inicial
        long diferencaMillis = dataFinal.getTimeInMillis() -
                								dataInicial.getTimeInMillis();
        // Quantidade de milissegundos em um dia
        int tempoDiaMillis = 1000 * 60 * 60 * 24;
        //
        long diferencaDias = diferencaMillis / tempoDiaMillis;
		//
		return diferencaDias;
	}
	
	public static String diaSemana(Date dataX) {
		//
		Locale objLocale = new Locale("pt", "BR");
		//
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataX);
		//
	  	String diaSemana = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, objLocale);
		//
		return diaSemana;
	}

	public static boolean isNumeric(String str) {  
		//
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		//
		return str.length() == pos.getIndex();
		//
//		try {  
//			Integer i = Integer.parseInt(str.trim());  
//		}  
//		catch(NumberFormatException nfe) {  
//			return false;  
//		}
//		//
//		return true;  
	}	

	public static String randomString(int tamanho, boolean usaLetras, boolean usaNumeros) {
	    //
	    String randomString = RandomStringUtils.random(tamanho, usaLetras, usaNumeros);
	    //
	    return randomString;
	}

	public static StringBuffer ConvertUrlToString(String webPage) {
		//
		try {
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			//
			return sb;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		return null;
	}

	public static MpAlarmeFilter capturaAlarme(Date dataAlarme) {
		//
		String diaSemana = MpAppUtil.diaSemana(dataAlarme);
		
		MpAlarmeFilter mpAlarmeFilter = new MpAlarmeFilter();

		switch(diaSemana.toUpperCase()) {
			case "DOMINGO" : mpAlarmeFilter.setIndDomingo(true); break;
			case "SEGUNDA-FEIRA" : mpAlarmeFilter.setIndSegunda(true); break;
			case "TERÇA-FEIRA" : mpAlarmeFilter.setIndTerca(true); break;
			case "QUARTA-FEIRA" : mpAlarmeFilter.setIndQuarta(true); break;
			case "QUINTA-FEIRA" : mpAlarmeFilter.setIndQuinta(true); break;
			case "SEXTA-FEIRA" : mpAlarmeFilter.setIndSexta(true); break;
			case "SÁBADO" : mpAlarmeFilter.setIndSabado(true); break;
		}
		//
		return mpAlarmeFilter;
	}

	public static String geraHmacSHA256(String secret, String message) {
		//
		String hash = "";
		//
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			
			sha256_HMAC.init(secret_key);

			hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return hash;
		//
	}
	
	public static String computeSignature(String mac, String message, String secret) {
		//
		String hash = "";
		//
		try {
//			String secret = "ddc8210fb85ce70253c7fde596be46e1";
//			String message = "4a5415ecmpxds";

//			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			Mac sha256_HMAC = Mac.getInstance(mac);

//			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), mac);

			sha256_HMAC.init(secret_key);

			hash = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes()));
			//					
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return hash;
		//
	}

    public static String sendNotification(String message, String appId)
    {
    	// Here you will write APP key given by Android end
    	Sender sender = new Sender("AIzaSyC1ymKZtFaAgMtwjHSfU5SCoJOMZt9_H20"); 
        
    	Message msg = new Message.Builder().addData("message", message).build();
        String str=null;
        try 
        {
            Result results = sender.send(msg, appId, 5); // Where appId is given by Android end
            if(results.getMessageId() != null)
            {
                str = "true" ; // val_true;
            }
            else
            {
                str= "false"; // val_false;
                String error = results.getErrorCodeName();
//                logger.info("message sending failed:: "+error);
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return str;
    }
    
    public static String verificaCpf(String string) {
    	// Retira pontos e traços do CPF ! 
    	string = string.replace(".", "");
    	string = string.replace("-", "");
    	//
		String saida = "";
		Pattern pattern = Pattern.compile("[0123456789]");
		Matcher matcher = pattern.matcher(string);
		//
		while (matcher.find()) {
			saida += matcher.group();
		}
		//
		if (saida.equalsIgnoreCase("00000000191")) {
			return "false";
		} else if (saida.equalsIgnoreCase("00000000000")) {
			return "false";
		} else if (saida.equalsIgnoreCase("11111111111")) {
			return "false";
		} else if (saida.equalsIgnoreCase("22222222222")) {
			return "false";
		} else if (saida.equalsIgnoreCase("33333333333")) {
			return "false";
		} else if (saida.equalsIgnoreCase("44444444444")) {
			return "false";
		} else if (saida.equalsIgnoreCase("55555555555")) {
			return "false";
		} else if (saida.equalsIgnoreCase("66666666666")) {
			return "false";
		} else if (saida.equalsIgnoreCase("77777777777")) {
			return "false";
		} else if (saida.equalsIgnoreCase("88888888888")) {
			return "false";
		} else if (saida.equalsIgnoreCase("99999999999")) {
			return "false";
		} else {
			return saida;
		}
	}

    public static byte[] getFileContents(InputStream in) {
    	//
		byte[] bytes = null;
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int read = 0;
			bytes = new byte[1024];
			//
			while ((read = in.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
			bytes = bos.toByteArray();
			//
			in.close();
			in = null;
			bos.flush();
			bos.close();
			bos = null;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}   

    public static String convertIS(InputStream inputStream, Charset charset) throws IOException {
    	//
    	StringBuilder stringBuilder = new StringBuilder();
    	String line = null;
    	
    	try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {	
    		while ((line = bufferedReader.readLine()) != null) {
    			stringBuilder.append(line);
    		}
    	}
    	//
    	return stringBuilder.toString();
    } 
        
    // Método static -> não precisa instânciar a classe ! 
    //
    public static void PrintarLn(String texto) {
    	//
    	System.out.println(texto);
    }
    
    // ----
    
    public static FacesContext getFC() { return FacesContext.getCurrentInstance(); }
    public static ExternalContext getEC() { return FacesContext.getCurrentInstance().getExternalContext(); }

    public static HttpServletRequest getRequest() { return (HttpServletRequest) getEC().getRequest(); }
    public static HttpServletResponse getResponse() { return (HttpServletResponse) getEC().getResponse(); }    
        
//    private String sendMulptipleNotifiaction(String message, ArrayList devices)
//    {
//        Sender sender = new Sender("AIzaSyCz2bnoLP-TzzpS-7AQNSYO87icMwpoj_g");// Here you will write APP key given by Android end
//        Message msg = new Message.Builder().addData("message", message).build();
//        String str=null;
//        try 
//        {
//            MulticastResult result = sender.send(msg, devices, 5); // where devices is the list of multiple device AppId's
//            for (Result r : result.getResults()) {
//                if (r.getMessageId() != null) {
//                    str = val_true;
//                }
//                else
//                {
//                    str= val_false;
//                    String error = r.getErrorCodeName();
//                    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
//                    }
//                }
//            } 
//        }
//        catch (IOException e) 
//        {
//            e.printStackTrace();
//        }
//        return str;
//    }
	
}