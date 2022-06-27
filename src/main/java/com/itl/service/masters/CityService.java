package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface CityService extends NGService {

	public CityMst getPrimaryKey(Long Id) throws NGException ;

	public CityMst getByCityId(String cityId) throws NGException;;
	
	public CityMst saveOrUpdate(String loginId, CityMst entity) throws NGException;
	
	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<CityMst> updateCacheList(String authStatus) throws NGException;
	
	public String getCityIdByCityName(String cityname)throws NGException;
}
