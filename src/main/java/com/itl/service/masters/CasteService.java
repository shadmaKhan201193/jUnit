package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.CasteMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface CasteService extends NGService {

	public CasteMst getPrimaryKey(Long Id) throws NGException;
	
	public CasteMst getByCasteId(String casteId) throws NGException;
	
	public CasteMst saveOrUpdate(String loginId, CasteMst entity) throws NGException;

	public List<CasteMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<CasteMst> updateCacheList(String authStatus) throws NGException;

	public List<CasteMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public String getCasteIdByCasteName(String casteName)throws NGException;
}
