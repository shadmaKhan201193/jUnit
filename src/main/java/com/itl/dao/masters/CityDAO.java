package com.itl.dao.masters;

import java.util.List;

import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;

public interface CityDAO  {

	//public EntityManagerFactory getEntityManagerFactory();
	
		public CityMst getPrimaryKey(Long id) throws NGException;
		
		public List<CityMst> getByCityName(String cityName) throws NGException;
		
		public List<CityMst> getByCityId(String cityId) throws NGException;
		
		public List<CityMst> getByCountryState(String countryId,String stateId,String cityId) throws NGException;
		
		public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
		public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException;

		public String getCityIdByCityName(String cityname) throws NGException;

//		public CityMst getPrimaryKey(Long id);
		
		//public String getCityIdByCityName(String cityname String cityId) throws NGException;
		
		public CityMst save(CityMst city) throws NGException;
}
