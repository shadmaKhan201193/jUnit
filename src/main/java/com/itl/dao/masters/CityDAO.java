package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;

public interface CityDAO extends JPADAO<CityMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public CityMst getPrimaryKey(Long Id) throws NGException;
	
	public List<CityMst> getByCityName(String cityName) throws NGException;
	
	public List<CityMst> getByCityId(String cityId) throws NGException;
	
	public List<CityMst> getByCountryState(String countryId,String stateId,String cityId) throws NGException;
	
	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getCityIdByCityName(String cityname) throws NGException;
}
