package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.ReligionMst;
import com.itl.exceptions.NGException;

public interface ReligionDAO extends JPADAO<ReligionMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public ReligionMst getPrimaryKey(Long Id) throws NGException;
	
	public List<ReligionMst> getByReligionName(String religionName) throws NGException;
	
	public List<ReligionMst> getByReligionId(String religionId) throws NGException;

	public List<ReligionMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<ReligionMst> getByDeleted(Boolean isDeleted) throws NGException;

	public ReligionMst getReligionIdByReligionName(String religionName)  throws NGException;


}
