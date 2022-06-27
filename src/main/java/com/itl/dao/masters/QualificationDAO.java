package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.QualificationMst;
import com.itl.exceptions.NGException;

public interface QualificationDAO extends JPADAO<QualificationMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public QualificationMst getPrimaryKey(Long Id) throws NGException;
	
	public List<QualificationMst> getByQualificationName(String qualificationName) throws NGException;
	
	public List<QualificationMst> getByQualificationId(String qualificationId) throws NGException;

	public List<QualificationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<QualificationMst> getByDeleted(Boolean isDeleted)throws NGException;

	public String getQualificationIdByQualificationName(String qualificationName) throws NGException;
	
	
	
}
