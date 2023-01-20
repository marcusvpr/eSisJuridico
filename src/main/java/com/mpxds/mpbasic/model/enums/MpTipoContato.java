package com.mpxds.mpbasic.model.enums;

public enum MpTipoContato {

	EMAIL("E-mail", "email.jpg"),
	SMS("Mensagem SMS", "sms.png"),
	ANDROID("Android MpComunicator", "android.jpg"),
	TELEGRAM("Telegram API", "telegram.jpg"),
	WHATSAPP("Whatsapp API", "WhatsApp.png"),
	PUSH("Push Notification Google", "googlePush.png");
	
	private String nome;
	private String imagem;
	
	// ---

	MpTipoContato(String nome,
				  String imagem) {
		this.nome = nome;
		this.imagem = imagem;
	}
	
	public String getNome() { return nome; }
	public String getImagem() { return imagem; }

}