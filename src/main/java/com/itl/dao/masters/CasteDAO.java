package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.CasteMst;
import com.itl.exceptions.NGException;

public interface CasteDAO extends JPADAO<CasteMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public CasteMst getPrimaryKey(Long Id) throws NGException;
	
	public List<CasteMst> getByCasteName(String casteName) throws NGException;
	
	public List<CasteMst> getByCasteId(String casteId) throws NGException;

	public CasteMst getByReligionCaste(String religionId,String casteId) throws NGException;

	public List<CasteMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<CasteMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getCasteIdByCasteName(String casteName)  throws NGException;
}
