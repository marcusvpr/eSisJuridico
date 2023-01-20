package com.mpxds.mpbasic.util.ws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

//import javax.ws.rs.core.MediaType;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.primefaces.json.JSONArray;
//import org.primefaces.json.JSONObject;

import com.mpxds.mpbasic.util.MpAppUtil;
//
import com.mpxds.mpbasic.util.ws.MpLeitorXML;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
//import com.mpxds.mpbasic.model.xml.MpAtividadeXML;
import com.mpxds.mpbasic.model.xml.MpCepXML;
import com.mpxds.mpbasic.model.xml.MpNegociacaoXML;
import com.mpxds.mpbasic.model.xml.multiFarma.Root;
import com.mpxds.mpbasic.model.xml.multiFarma.Roots;
import com.mpxds.mpbasic.model.xml.multiFarma.MpMultiFarmaXML;
//import com.mpxds.mpbasic.model.xml.multiFarma.Posologia;

// ---

public class MpClienteWebService {

	private static int HTTP_COD_SUCESSO = 200;

	private static final String URL_NEGOCIACAO_WS = "http://argentumws.caelum.com.br/negociacoes";

	private static final String URL_CEP_WS = "http://api.postmon.com.br/v1/cep/";

	private static final String URL_MULTIFARMA_WS = "http://api.multifarmas.com.br/comparar/parceiro";

//	private static final String REST_URI_MP_ATIVIDADE_WS = 
//													"http://localhost:8080/mpProtesto/mpServices/mpAtividadeWS/";

	private static final String URL_E_CARTORIO_RJ_WS = "https://ws.homologacao.com.br/ws/public/Service.svc?wsdl";

	// ---

	public static MpCepXML executaCep(String cep) {
		//
		MpCepXML mpCepXML = new MpCepXML();
		//
		try {
			URL url = new URL(URL_CEP_WS + cep + "?format=xml");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				// throw new RuntimeException("HTTP error code : "+
				// con.getResponseCode());
				mpCepXML.setComplemento("HTTP error code : " + con.getResponseCode());
			}
			//
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(con.getInputStream()));
			// //
			// String line = "";
			// String text = "";
			// //
			// while ((line = br.readLine()) != null) {
			// text += line;
			// //
			// System.out.println("CepXML.executaCep()... " + text);
			// }
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(MpCepXML.class);
			//
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//
			mpCepXML = (MpCepXML) jaxbUnmarshaller.unmarshal(url);
			//
			// System.out.println("------ Dados do CEP -------- \n");
			// System.out.println("CEP : " + cepXML.getCep());
			// System.out.println("Logradouro : " + cepXML.getLogradouro());
			//
			con.disconnect();
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		return mpCepXML;
	}

	public static List<MpNegociacaoXML> getNegociacoesXML() {

		HttpURLConnection connection = null;
		//
		try {
			URL url = new URL(URL_NEGOCIACAO_WS);

			connection = (HttpURLConnection) url.openConnection();

			InputStream content = connection.getInputStream();

			return new MpLeitorXML().carregaNegociacaoXML(content);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			connection.disconnect();
		}
	}

	// public static MpMultiFarmaXML executaMultiFarma(String buscar, String
	// ordenar) {
	// //
	// MpMultiFarmaXML mpMultiFarmaXML = new MpMultiFarmaXML();
	// //
	// String charset = "UTF-8"; // Or in Java 7 and later, use the constant:
	// java.nio.charset.StandardCharsets.UTF_8.name()
	// String parceiro_id = "77";
	// String api_id = "4a5415ec";
	// String dominio = "mpxds";
	// String private_key = "ddc8210fb85ce70253c7fde596be46e1";
	//
	// String hash = MpAppUtil.geraHmacSHA256(api_id + "." + dominio,
	// private_key);
	//
	// String formato = "XML";
	// //
	// String queryParam = "formato=%s" +
	// "&" + "parceiro_id=%s" +
	// "&" + "dominio=%s" +
	// "&" + "api_id=%s" +
	// "&" + "hash=%s" +
	// "&" + "ordenar=%s" +
	// "&" + "buscar=%s" ;
	// //
	// try {
	// //
	// String query = String.format(queryParam,
	// URLEncoder.encode(formato, charset),
	// URLEncoder.encode(parceiro_id, charset),
	// URLEncoder.encode(dominio, charset),
	// URLEncoder.encode(api_id, charset),
	// URLEncoder.encode(hash, charset),
	// URLEncoder.encode(ordenar, charset),
	// URLEncoder.encode(buscar, charset));
	// //
	// System.out.println("MpClienteWebService.executaMultiFarma (Buscar = " +
	// buscar +
	// " / Ordenar = " + ordenar + " / Hash = " + hash + " / Query = " + query);
	// //
	// URL url = new URL(URL_MULTIFARMA_WS + "?"+ query);
	//
	// HttpURLConnection con = (HttpURLConnection) url.openConnection();
	// //
	// if (con.getResponseCode() != HTTP_COD_SUCESSO) {
	// // throw new RuntimeException("HTTP error code : "+
	// // con.getResponseCode());
	// mpMultiFarmaXML.setComplemento("HTTP error code : " +
	// con.getResponseCode());
	// }
	// //
	// JAXBContext jaxbContext = JAXBContext.newInstance(MpMultiFarmaXML.class);
	// //
	// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	// //
	// mpMultiFarmaXML = (MpMultiFarmaXML) jaxbUnmarshaller.unmarshal(url);
	// //
	// System.out.println("------ Dados MULTIFARMA -------- \n");
	// System.out.println("CEP : " + mpMultiFarmaXML.getComplemento());
	// //
	// con.disconnect();
	// //
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (JAXBException e) {
	// e.printStackTrace();
	// }
	// //
	// return mpMultiFarmaXML;
	// }

	public static Root executaMultiFarmaPost(String buscar, String ordenar) {
		//
		Root root = new Root();
		// Java para JSON
		// User user = new User(123, "Wektabyte");
		// Gson gson = new Gson();
		// System.out.println(gson.toJson(user));
		// // Saída: {"id":123,"firstName":"Wektabyte"}

		// JSON para Java
		// String json = "{\"id\":123,\"firstName\":\"Wektabyte\"}";
		// Gson gson = new Gson();
		// User user = gson.fromJson(json, User.class); // retorna um objeto
		// User.
		// System.out.println(String.format("%d-%s", user.getId(),
		// user.getFirstName()));
		// // Saída: id: 123; first name: Wektabyte
		//
		// MpMultiFarmaXML xmpMultiFarmaXML = new MpMultiFarmaXML();

		// jaxbObjectToXML(xmpMultiFarmaXML);

		// MpMultiFarmaAtributoXML xmpMultiFarmaAtributoXML = new
		// MpMultiFarmaAtributoXML();
		// MpMultiFarmaProdutoXML xmpMultiFarmaProdutoXML = new
		// MpMultiFarmaProdutoXML();
		// List<MpMultiFarmaProdutoXML> xmpMultiFarmaProdutoXMLs =
		// new ArrayList<MpMultiFarmaProdutoXML>();
		// xmpMultiFarmaProdutoXMLs.add(xmpMultiFarmaProdutoXML);
		// xmpMultiFarmaProdutoXMLs.add(xmpMultiFarmaProdutoXML);
		// xmpMultiFarmaProdutoXMLs.add(xmpMultiFarmaProdutoXML);
		// xmpMultiFarmaXML.setMpMultiFarmaAtributoXML(xmpMultiFarmaAtributoXML);
		// xmpMultiFarmaXML.setMpMultiFarmaProdutoXMLs(xmpMultiFarmaProdutoXMLs);

		// Gson xgson = new Gson();
		//
		// System.out.println("MpClienteWebService.executaMultiFarmaPost()
		// gson.toJson ( " +
		// xgson.toJson(xmpMultiFarmaXML));
		// //
		// List<MpMultiFarmaXML> mpMultiFarmaXMLs = new
		// ArrayList<MpMultiFarmaXML>();
		//
		String formato = "xml"; // "json";
		String parceiro_id = "77";
		String api_id = "4a5415ec";
		String dominio = "mpxds";
		// String charset = "UTF-8"; // Or in Java 7 and later, use the
		// constant: java.nio.charset.StandardCharsets.UTF_8.name()
		String private_key = "ddc8210fb85ce70253c7fde596be46e1";

		// String hash = MpAppUtil.geraHmacSHA256(api_id + "." + dominio,
		// private_key);

		String hash = MpAppUtil.computeSignature("HmacSHA256", api_id + dominio, private_key);

		// Scanner in = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpPost post = new HttpPost(URL_MULTIFARMA_WS);

			// add header
			post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US;"
					+ " rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

			List<NameValuePair> params = new ArrayList<>();

			params.add(new BasicNameValuePair("formato", formato));
			params.add(new BasicNameValuePair("parceiro_id", parceiro_id));
			params.add(new BasicNameValuePair("parceiro_dominio", dominio));
			params.add(new BasicNameValuePair("parceiro_api_id", api_id));
			params.add(new BasicNameValuePair("parceiro_assinatura", hash));
			params.add(new BasicNameValuePair("buscar", buscar));
			params.add(new BasicNameValuePair("ordenar", ordenar));

			CloseableHttpResponse response = null;

			post.setEntity(new UrlEncodedFormEntity(params));
			//
			// System.out.println("MpClienteWebService.executaMultiFarmaPost() (
			// " + params.toString());

			response = httpClient.execute(post); // POST
			// CONVERT RESPONSE TO STRING
			String result = EntityUtils.toString(response.getEntity());

			// System.out.println("MpClienteWebService.executaMultiFarmaPost 00
			// ( " +
			// result + " / " + response.getStatusLine());

			// result = "[" + result + "]";

			InputStream in = IOUtils.toInputStream(result, "UTF-8");

			JAXBContext jaxbContext;
			
			try {
				jaxbContext = JAXBContext.newInstance(Roots.class);
				
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				// We had written this file in marshalling example
				root = (Root) jaxbUnmarshaller.unmarshal(in);

				// for (Posologia posologia : root.getPosologia()) {
				// //
				// System.out.println("MpClienteWebService.executaMultiFarmaPost
				// 00 ( Posologia = " +
				// posologia.getId() + " / " +
				// posologia.getProduto().size());
				// }
				//
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			// root = jaxbXMLToObjectRoot(in);

			// List<Root> roots = carregaMultiFarmaRootXML(in);
			//
			// System.out.println("MpClienteWebService.executaMultiFarmaPost
			// 00-1 ( roots = " +
			// roots.size());

			// for (Root rootx : roots) {
			// //
			//
			// }

			// // CONVERT RESPONSE STRING TO JSON ARRAY
			// JSONArray ja = new JSONArray(result);
			//
			// // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			// int n = ja.length();
			//
			// System.out.println("MpClienteWebService.executaMultiFarmaPost 01
			// ( " + result +
			// " / ja.length() = " + n);
			//
			// for (int i = 0; i < n; i++) {
			// // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
			// JSONObject jo = ja.getJSONObject(i);
			//
			// System.out.println("MpClienteWebService.executaMultiFarmaPost 02
			// ( " +
			// "[" + jo.toString() + "]");
			// Gson gson = new Gson();
			//
			// Type listType = new TypeToken<ArrayList<MpMultiFarmaXML>>()
			// {}.getType();
			// MpMultiFarmaXML mpMultiFarmaXML = gson.fromJson("[" +
			// jo.toString() + "]", listType);

			// Root root = gson.fromJson("[" + jo.toString() + "]", Root.class);

			// Type listType = new TypeToken<ArrayList<MpMultiFarmaXML>>()
			// {}.getType();
			// List<MpMultiFarmaXML> yourClassList = new
			// Gson().fromJson(ja.toString(), listType);
			//
			// for (MpMultiFarmaXML xmpMultiFarmaXML : yourClassList) {
			// if (null == mpMultiFarmaXML)
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " xmpMultiFarmaXML NULL");
			// else
			// if (null == mpMultiFarmaXML.getRoot())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Root NULL");
			// else
			// if (null == mpMultiFarmaXML.getRoot().getPosologia())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia NULL");
			// else
			// if (null == mpMultiFarmaXML.getRoot().getPosologia().getThumb())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getThumb() NULL");
			// else {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getThumb() = " +
			// mpMultiFarmaXML.getRoot().getPosologia().getThumb());
			//
			// if (null ==
			// mpMultiFarmaXML.getRoot().getPosologia().getProduto())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " getPosologia.getProduto() NULL ");
			// else
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto().length = " +
			// mpMultiFarmaXML.getRoot().getPosologia().getProduto().length);
			// }
			// }
			//
			// if (null == root)
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " root NULL");
			// else
			// if (null == root.getPosologia())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia NULL");
			// else
			// if (null == root.getPosologia().getThumb()) {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " " + "Posologia.getThumb() NULL");
			// if (null == root.getPosologia().getProduto())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto() NULL ");
			// else
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto().size = " +
			// root.getPosologia().getProduto().size());
			//
			// for (Produto produtox : root.getPosologia().getProduto()) {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto().produto = " +
			// produtox.getProduto_nome() + "/" +
			// produtox.getProduto_preco());
			// }
			// } else {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getThumb() = " + root.getPosologia().getThumb());
			//
			// if (null == root.getPosologia().getProduto())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto() NULL ");
			// else
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " Posologia.getProduto().size = " +
			// root.getPosologia().getProduto().size());
			// }

			// // RETRIEVE EACH JSON OBJECT'S FIELDS
			// String produto_nome = jo.getString("produto_nome");
			// String produto_id = jo.getString("produto_id");
			// String produto_link = jo.getString("produto_link");
			// String produto_preco = jo.getString("produto_preco");
			// String produto_cod_barra = jo.getString("produto_cod_barra");
			// String produto_thumb = jo.getString("produto_thumb");
			// String produto_cod_farmacia =
			// jo.getString("produto_cod_farmacia");
			// String produto_principio_ativo =
			// jo.getString("produto_principio_ativo");
			// String produto_ultima_atualizacao =
			// jo.getString("produto_ultima_atualizacao");
			// String produto_laboratorio = jo.getString("produto_laboratorio");
			// String farmacia_thumb = jo.getString("farmacia_thumb");
			// String farmacia_nome = jo.getString("farmacia_nome");
			// String produto_posologia = jo.getString("produto_posologia");
			// String bula_id = jo.getString("bula_id");
			//
			//// long id = jo.getLong("id");
			//// String name = jo.getString("name");
			//// String address = jo.getString("address");
			//// String country = jo.getString("country");
			//// String zip = jo.getString("zip");
			//// double clat = jo.getDouble("lat");
			//// double clon = jo.getDouble("lon");
			//// String url = jo.getString("url");
			//// String number = jo.getString("number");
			//
			// // CONVERT DATA FIELDS TO CLUB OBJECT
			// MpMultiFarmaXML mpMultiFarmaXML = new
			// MpMultiFarmaXML(produto_nome,
			// produto_id, produto_link, produto_preco,
			// produto_cod_barra, produto_thumb, produto_cod_farmacia,
			// produto_principio_ativo, produto_ultima_atualizacao,
			// produto_laboratorio, farmacia_thumb, farmacia_nome,
			// produto_posologia, bula_id);
			//
			//// Club c = new Club(id, name, address, country, zip, clat, clon,
			// url, number);
			//

			// mpMultiFarmaXMLs.add(mpMultiFarmaXML);
			// }

			// List<MpMultiFarmaRootXML> ympMultiFarmaRootXMLs = new
			// MpLeitorXML().
			// carregaMultiFarmaRootXML(response.getEntity().getContent());
			//
			// for (MpMultiFarmaRootXML mpMultiFarmaRootXML :
			// ympMultiFarmaRootXMLs) {

			// --------------------

			// Produto[] produtos = new Produto[3];
			//
			// Produto produto = new Produto();
			// produto.setProduto_id("yyy_produtoId");
			// produto.setProduto_nome("yyy_produtoNome");
			//
			// produtos[0] = produto;
			// produtos[1] = produto;
			// produtos[2] = produto;
			//
			// Posologia posologia = new Posologia();
			// posologia.setId("xxx_id");
			// posologia.setThumb("xxx_thumb");
			// posologia.setProduto(produtos);
			//
			// Root root = new Root();
			// root.setPosologia(posologia);
			//
			// MpMultiFarmaXML mpMultiFarmaXML = new MpMultiFarmaXML();
			// mpMultiFarmaXML.setRoot(root);
			//
			// jaxbObjectToXMLRoot(root);

			// ==============================================================

			// mpMultiFarmaXML =
			// jaxbXMLToObject(response.getEntity().getContent());
			// if (null == mpMultiFarmaXML)
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " mpMultiFarmaXML NULL");
			// else if (null == mpMultiFarmaXML.getRoot())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " root NULL");
			// else if (null == mpMultiFarmaXML.getRoot().getPosologia())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " posologia NULL");
			// else if (null ==
			// mpMultiFarmaXML.getRoot().getPosologia().getThumb())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " posologia.getThumb() NULL");
			// else {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " posologia.getThumb() = "
			// + mpMultiFarmaXML.getRoot().getPosologia().getThumb());
			//
			// if (null ==
			// mpMultiFarmaXML.getRoot().getPosologia().getProduto())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " posologia.getProduto() NULL ");
			// else
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " mpPosologia.getProduto().length = " +
			// " / " + mpMultiFarmaXML.getRoot().
			// getPosologia().getProduto().length);
			// }

			// Root rootX =
			// jaxbXMLToObjectRoot(response.getEntity().getContent());
			// if (null == rootX)
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " rootX NULL");
			// else if (null == rootX.getPosologia())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost =
			// posologia NULL");
			// else if (null == rootX.getPosologia().getThumb())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " posologia.getThumb() NULL");
			// else {
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// +
			// " posologiaXML.getThumb() = " + rootX.getPosologia().getThumb());
			//
			// if (null == rootX.getPosologia().getProduto())
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " mpMultiFarmaPosologiaXML.getProduto() NULL ");
			// else
			// System.out.println("MpClienteWebService.executaMultiFarmaPost = "
			// + " mpMultiFarmaPosologiaXML.getProduto().length = " +
			// " / " + rootX.getPosologia().getProduto().length);
			// }

			// }

			// HttpEntity entity = response.getEntity();
			// in = new Scanner(entity.getContent());
			//
			// while (in.hasNext()) {
			// //
			// System.out.println("MpClienteWebService.executaMultiFarmaPost 01
			// ( " + in.next());
			// }
			// //
			// EntityUtils.consume(entity);
			//
			// in.close();

			// response.close();
			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		// //
		// return mpMultiFarmaXMLs;
		//
		return root;
	}

	public static MpMultiFarmaXML jaxbXMLToObject(InputStream is) {
		try {
			JAXBContext context = JAXBContext.newInstance(MpMultiFarmaXML.class);

			Unmarshaller un = context.createUnmarshaller();

			// MpMultiFarmaXML mpMultiFarmaXML = (MpMultiFarmaXML)
			// un.unmarshal(new File(
			// "jaxb-multifarma.xml"));
			MpMultiFarmaXML mpMultiFarmaXML = (MpMultiFarmaXML) un.unmarshal(is);

			return mpMultiFarmaXML;
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		//
		return null;
	}

	public static Root jaxbXMLToObjectRoot(InputStream is) {
		try {
			JAXBContext context = JAXBContext.newInstance(Root.class);

			Unmarshaller un = context.createUnmarshaller();

			// MpMultiFarmaXML mpMultiFarmaXML = (MpMultiFarmaXML)
			// un.unmarshal(new File(
			// "jaxb-multifarma.xml"));
			Root root = (Root) un.unmarshal(is);

			return root;
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		//
		return null;
	}

	public static void jaxbObjectToXML(MpMultiFarmaXML mpMultiFarmaXML) {
		//
		try {
			String filename = "MpMultiFarmaXML.xml";

			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().
																getExternalContext().getContext();

			String fileX = servletContext.getRealPath("") + File.separator + "resources" + 
												File.separator + "xmls"	+ File.separator + filename;

			JAXBContext context = JAXBContext.newInstance(MpMultiFarmaXML.class);

			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// System.out.println("MpClienteWebService.executaMultiFarmaPost
			// jaxbObjectToXML ( " +
			// fileX);
			// Write to File
			m.marshal(mpMultiFarmaXML, new File(fileX));
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void jaxbObjectToXMLRoot(Root root) {
		//
		try {
			String filename = "Root.xml";

			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().
																getExternalContext().getContext();

			String fileX = servletContext.getRealPath("") + File.separator + "resources" +
												File.separator + "xmls" + File.separator + filename;

			JAXBContext context = JAXBContext.newInstance(Root.class);

			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// System.out.println("MpClienteWebService.executaMultiFarmaPost
			// jaxbObjectToXMLRoot ( " +
			// fileX);
			// Write to File
			m.marshal(root, new File(fileX));
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Root> carregaMultiFarmaRootXML(InputStream inputStream) {
		XStream stream = new XStream(new DomDriver());

		stream.alias("root", Root.class);

		return (List<Root>) stream.fromXML(inputStream);
	}

	public static Roots jaxbXMLToObjectRoots(InputStream is) {
		try {
			JAXBContext context = JAXBContext.newInstance(Root.class);

			Unmarshaller un = context.createUnmarshaller();

			// MpMultiFarmaXML mpMultiFarmaXML = (MpMultiFarmaXML)
			// un.unmarshal(new File(
			// "jaxb-multifarma.xml"));
			Roots roots = (Roots) un.unmarshal(is);

			return roots;
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		//
		return null;
	}

//	public static MpAtividadeXML RestWBClientMpAtividade() {
//		//
//		MpAtividadeXML mpAtividadeXML = new MpAtividadeXML();
//		
//    	ClientConfig config = new DefaultClientConfig();   
//        Client client = Client.create(config);
//        
//        WebResource service = client.resource(REST_URI_MP_ATIVIDADE_WS);
//        WebResource msgService = service.path("10");
//        
//        String msg = msgService.accept(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println(msg);		
//		
//		return mpAtividadeXML;
//	}

	public static Root executaECartorioRjPost(String buscar, String ordenar) {
		//
		Root root = new Root();
		//
		String formato = "xml"; // "json";
		String parceiro_id = "77";
		String api_id = "4a5415ec";
		String dominio = "mpxds";
		// String charset = "UTF-8"; // Or in Java 7 and later, use the
		// constant: java.nio.charset.StandardCharsets.UTF_8.name()
		String private_key = "ddc8210fb85ce70253c7fde596be46e1";

		// String hash = MpAppUtil.geraHmacSHA256(api_id + "." + dominio,
		// private_key);

		String hash = MpAppUtil.computeSignature("HmacSHA256", api_id + dominio, private_key);

		// Scanner in = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpPost post = new HttpPost(URL_E_CARTORIO_RJ_WS);

			// add header
			post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US;"
					+ " rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

			List<NameValuePair> params = new ArrayList<>();

			params.add(new BasicNameValuePair("formato", formato));
			params.add(new BasicNameValuePair("parceiro_id", parceiro_id));
			params.add(new BasicNameValuePair("parceiro_dominio", dominio));
			params.add(new BasicNameValuePair("parceiro_api_id", api_id));
			params.add(new BasicNameValuePair("parceiro_assinatura", hash));
			params.add(new BasicNameValuePair("buscar", buscar));
			params.add(new BasicNameValuePair("ordenar", ordenar));

			CloseableHttpResponse response = null;

			post.setEntity(new UrlEncodedFormEntity(params));
			//
			// System.out.println("MpClienteWebService.executaMultiFarmaPost() (
			// " + params.toString());

			response = httpClient.execute(post); // POST
			// CONVERT RESPONSE TO STRING
			String result = EntityUtils.toString(response.getEntity());

			// System.out.println("MpClienteWebService.executaMultiFarmaPost 00
			// ( " +
			// result + " / " + response.getStatusLine());

			// result = "[" + result + "]";

			InputStream in = IOUtils.toInputStream(result, "UTF-8");

			JAXBContext jaxbContext;
			
			try {
				jaxbContext = JAXBContext.newInstance(Roots.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				// We had written this file in marshalling example
				root = (Root) jaxbUnmarshaller.unmarshal(in);
				//
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			//
		} catch (IOException e) {
			e.printStackTrace();
		}
		// //
		// return mpMultiFarmaXMLs;
		//
		return root;
	}
	
}