package com.mpxds.mpbasic.model.enums.sisJuri;

import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;

public enum MpTipoTabelaInternaSJ {
	//
	TAB_0000("0000", "Tabela Definir (SJ)", "c", 50, false, null),
	TAB_0001("0001", "Tabela Cidade", "c", 50, false, null),
	TAB_0003("0003", "Tabela Cor", "c", 30, false, null),
	TAB_0005("0005", "Tabela Estado (UF)", "c", 20, false, null),
	TAB_0007("0007", "Tabela Grupo ImagemBD", "c", 30, false, null),
	TAB_0009("0009", "Tabela Localização", "c", 50, false, null),
	TAB_0010("0010", "Tabela Cidade (SJ)", "c", 50, false, null),
	TAB_0015("0015", "Tabela Tipo (SJ)", "c", 50, false, null),
	TAB_0025("0025", "Tabela Grupo (SJ)", "c", 50, false, null),
	TAB_0030("0030", "Tabela Forma Tributária (SJ)", "c", 50, false, null),
	TAB_0040("0040", "Tabela Grupo Menu (SJ)", "c", 50, false, null),
	TAB_0045("0045", "Tabela Ramo Atividade (SJ)", "c", 50, false, null),
	TAB_0055("0055", "Tabela Unidade (SJ)", "c", 50, false, null),
	TAB_0060("0060", "Tabela Estado Civil (SJ)", "c", 50, false, null),
	TAB_0070("0070", "Tabela Chamado Tipo (SJ)", "c", 50, false, null),
	TAB_0075("0075", "Tabela Area Tipo (SJ)", "c", 50, false, null),
	TAB_0080("0080", "Tabela Severidade (SJ)", "c", 50, false, null),
	TAB_0085("0085", "Tabela Chamado Status (SJ)", "c", 50, false, null),

	TAB_1006("1006", "Tabela Processo Tipo (SJ)", "c", 50, false, null), 
	TAB_1007("1007", "Tabela Ação Natureza (SJ)", "c", 50, false, null), 
	TAB_1008("1008", "Tabela Comarca (SJ)", "c", 50, true, "TAB_1042"),
	TAB_1010("1010", "Tabela Especialidade (SJ)", "c", 50, false, null), 
	TAB_1014("1014", "Tabela Area (SJ)", "c", 50, false, null), 
	TAB_1017("1017", "Tabela Ação Rito (SJ)", "c", 50, false, null), 
	TAB_1018("1018", "Tabela Pagamento Parte (SJ)", "c", 50, false, null), 
	TAB_1021("1021", "Tabela Objeto Situação (SJ)", "c", 50, false, null), 
	TAB_1022("1022", "Tabela Andamento Tipo (SJ)", "c", 50, false, null), 
	TAB_1023("1023", "Tabela Compromisso Tipo (SJ)", "c", 50, false, null), 
	TAB_1025("1025", "Tabela Processo Fase (SJ)", "c", 50, false, null), 
	TAB_1026("1026", "Tabela Posição Parte (SJ)", "c", 50, false, null), 
	TAB_1027("1027", "Tabela Instância (SJ)", "c", 50, false, null), 
	TAB_1028("1028", "Tabela Vara (SJ)", "c", 50, false, null), 
	TAB_1031("1031", "Tabela Pedido Tipo (SJ)", "c", 50, false, null), 
	TAB_1032("1032", "Tabela Objeto Tipo (SJ)", "c", 50, false, null), 
	TAB_1035("1035", "Tabela Anexo Tipo (SJ)", "c", 50, false, null), 
	TAB_1040("1040", "Tabela Ato Praticado (SJ)", "c", 50, false, null), 
	TAB_1041("1041", "Tabela Resultado Audiência (SJ)", "c", 50, false, null), 
	TAB_1042("1042", "Tabela Cartório - Comarca  (SJ)", "c", 50, false, null), // Filha -> Tab.1008 ...
	TAB_1055("1055", "Tabela Banco (SJ)", "c", 50, false, null), 
	TAB_1060("1060", "Tabela Orgão Número (SJ)", "c", 50, false, null), 
	TAB_1070("1070", "Tabela Andamento Detalhamento (SJ)", "c", 50, false, null); 

	private final String tabela;
	private final String descricao;
	private final String formato; // c=character n=numeric
	private final Integer tamanho;
	private final Boolean indPai;
	private final String filha;
	
	// ---

	MpTipoTabelaInternaSJ(String tabela, String descricao, String formato, Integer tamanho,
						  Boolean indPai, String filha) {
		this.tabela = tabela;
		this.descricao = descricao;
		this.formato = formato;
		this.tamanho = tamanho;
		this.indPai = indPai;
		this.filha = filha;
	}
	
//    public static MpTipoTabelaInternaSJ capturaTipoTabela(String tabelaX) {
//    	//
//    	MpTipoTabelaInternaSJ mpTipoTabelaInternaSJX = null; // Default
//    	
//        for (MpTipoTabelaInternaSJ itemX : MpTipoTabelaInternaSJ.values()) {
//            if (itemX.getTabela().equals(tabelaX)) {
//            	mpTipoTabelaInternaSJX = itemX;
//                break;
//            }
//        }
//        //
//        return mpTipoTabelaInternaSJX;
//    }	
	
	// ---
	
	public String getTabela() { return tabela; }

	public String getDescricao() { return descricao; }
	
	public String getFormato() { return formato; }
	
	public Integer getTamanho() { return tamanho; }
	
	public Boolean getIndPai() { return indPai; }
	
	public String getFilha() { return filha; }
	
}