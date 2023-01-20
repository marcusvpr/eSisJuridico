package com.mpxds.mpbasic.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

@ApplicationScoped
public class MpEntityManagerProducer {

	private EntityManagerFactory factory;
	
	public MpEntityManagerProducer() {
		//
		this.factory = Persistence.createEntityManagerFactory("MpProtestoPU");
	}
	
	@Produces @RequestScoped
	public Session createEntityManager() {
		//
		return (Session) this.factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes Session manager) {
		//
		manager.close();
	}
	
}