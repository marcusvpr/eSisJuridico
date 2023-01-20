package com.mpxds.mpbasic.model.enums;

public enum MpMenuGlobalGrupo {
	//
	G01(1, "ADMINISTRADORES", "ADMINISTRADORES", "ATIVO"),
	G02(2, "VENDEDORES", "VENDEDORES", "ATIVO"),
	G03(3, "AUXILIARES", "AUXILIARES", "ATIVO"),
	G04(4, "USUARIOS", "USUARIOS", "ATIVO"),
	G05(5, "PROTESTOS_ADMIN", "PROTESTOS_ADMIN", "ATIVO"),
	G06(6, "PROTESTOS", "PROTESTOS", "ATIVO"),
	G07(7, "IFFS_ADMIN", "IFFS_ADMIN", "ATIVO"),
	G08(8, "ENGREQ_ADMIN", "ENGREQ_ADMIN", "ATIVO"),
	G09(9, "SK_ADMIN", "SK_ADMIN", "ATIVO");
	
	private Integer id;
	private String nome;
	private String descricao;
	private String mpStatus;
	
	// ---
	
	MpMenuGlobalGrupo(Integer id, 
				String nome,
				String descricao,
				String mpStatus) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.mpStatus = mpStatus;
	}

	public Integer getId() { return id; }
	
	public String getNome() { return this.nome; }

	public String getDescricao() { return this.descricao; }

	public String getMpStatus() { return this.mpStatus; }
	
}