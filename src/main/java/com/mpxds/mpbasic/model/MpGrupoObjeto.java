package com.mpxds.mpbasic.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mpxds.mpbasic.model.enums.MpStatusObjeto;

@Entity
@Table(name="mp_grupo_objeto")
public class MpGrupoObjeto extends MpBaseEntity {
	//
	private static final long serialVersionUID = 1L;
	//
	private boolean indSeparator = false;
	//
    private MpGrupo mpGrupo;
	private MpObjeto mpObjeto;
	private MpStatusObjeto mpStatusObjeto;
		
	// ---
	
	public MpGrupoObjeto() {
		super();
	}
	
	public MpGrupoObjeto(MpGrupo mpGrupo
            		  , MpObjeto mpObjeto
            		  , MpStatusObjeto mpStatusObjeto 
					) {
			this.mpGrupo = mpGrupo; 
			this.mpObjeto = mpObjeto; 
			this.mpStatusObjeto = mpStatusObjeto; 
	}

	@Column(nullable = false, name = "ind_separator")
	public Boolean getIndSeparator() {
		return this.indSeparator;
	}
	public void setIndSeparator(Boolean newIndSeparator) {
		this.indSeparator = newIndSeparator;
	}
	
    @ManyToOne
    @JoinColumn(name="mpGrupo_id")
	public MpGrupo getMpGrupo() {
		return this.mpGrupo;
	}
	public void setMpGrupo(MpGrupo mpGrupo) {
		this.mpGrupo = mpGrupo;
	}
  
	@OneToOne(fetch=FetchType.EAGER) 
	@JoinColumn(name="mpOobjeto_id")
    public MpObjeto getMpObjeto() {
        return this.mpObjeto;
    }
    public void setMpObjeto(MpObjeto mpObjeto) {
        this.mpObjeto = mpObjeto;
    }

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 10, name = "mpStatus_objeto")
	public MpStatusObjeto getMpStatusObjeto() {
		return this.mpStatusObjeto;
	}
	public void setMpStatusObjeto(MpStatusObjeto newMpStatusObjeto) {
		this.mpStatusObjeto = newMpStatusObjeto;
	}
	
}
