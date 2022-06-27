package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.AuthStatusMst;
import com.itl.exceptions.NGException;

public interface AuthStatusDAO extends JPADAO<AuthStatusMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public AuthStatusMst getPrimaryKey(Long Id) throws NGException;
	
	public List<AuthStatusMst> getByAuthStatusName(String authStatusName) throws NGException;
	
	public List<AuthStatusMst> getByAuthStatusId(String authStatusId) throws NGException;
	
}
