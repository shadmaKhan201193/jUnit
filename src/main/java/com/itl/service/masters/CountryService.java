package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.CountryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface CountryService extends NGService {

	public CountryMst getPrimaryKey(Long Id) throws NGException ;

	public CountryMst getByCountryId(String countryId) throws NGException;
	
	public CountryMst saveOrUpdate(String loginId, CountryMst entity) throws NGException;
	
	public List<CountryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<CountryMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<CountryMst> updateCacheList(String authStatus) throws NGException;

	public String getCountryIdByCountryName(String Countryname)throws NGException;
	
}
