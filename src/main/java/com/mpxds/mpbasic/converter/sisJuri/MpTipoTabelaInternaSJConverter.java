package com.mpxds.mpbasic.converter.sisJuri;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.mpxds.mpbasic.model.enums.sisJuri.MpTipoTabelaInternaSJ;

@Converter(autoApply = true)
public class MpTipoTabelaInternaSJConverter implements AttributeConverter<MpTipoTabelaInternaSJ, String> {

    @Override
    public String convertToDatabaseColumn(MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ) {
        return mpTipoTabelaInternaSJ.getTabela();
    }

    @Override
    public MpTipoTabelaInternaSJ convertToEntityAttribute(String dbData) {
        for (MpTipoTabelaInternaSJ mpTipoTabelaInternaSJ : MpTipoTabelaInternaSJ.values()) {
            if (mpTipoTabelaInternaSJ.getTabela().equals(dbData)) {
                return mpTipoTabelaInternaSJ;
            }
        }
        //
        throw new IllegalArgumentException("Valor desconhecido Bando Dados : ( " + dbData + " ) ");
    }
}