package com.itl.service.masters;

import java.util.List;


import com.itl.domain.entities.masters.QualificationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface QualificationService extends NGService {

	public QualificationMst getPrimaryKey(Long Id) throws NGException ;
	
	public QualificationMst getByQualificationId(String qualificationId) throws NGException;
	
	public QualificationMst saveOrUpdate(String loginId, QualificationMst entity) throws NGException;

	public List<QualificationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<QualificationMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<QualificationMst> updateCacheList(String authStatus) throws NGException;

	public String getQualificationIdByQualificationName(String qualificationName)throws NGException;
}
