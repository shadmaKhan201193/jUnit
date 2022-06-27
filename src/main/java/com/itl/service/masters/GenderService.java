package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.GenderMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface GenderService extends NGService {

	public GenderMst getPrimaryKey(Long Id) throws NGException ;

	public GenderMst getByGenderId(String genderId) throws NGException;
	
	public GenderMst saveOrUpdate(String loginId, GenderMst entity) throws NGException;
	
	public List<GenderMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<GenderMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<GenderMst> updateCacheList(String authStatus) throws NGException;

	public String getGenderIdByGenderName(String GenderName)throws NGException;

}
