package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.GenderMst;
import com.itl.exceptions.NGException;

public interface GenderDAO extends JPADAO<GenderMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public GenderMst getPrimaryKey(Long Id) throws NGException;
	
	public List<GenderMst> getByGenderName(String genderName) throws NGException;
	
	public List<GenderMst> getByGenderId(String genderId) throws NGException;

	public List<GenderMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<GenderMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getGenderIdByGenderName(String genderName) throws NGException;
	
}
