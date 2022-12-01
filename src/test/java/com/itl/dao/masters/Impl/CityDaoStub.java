package com.itl.dao.masters.Impl;

import java.util.List;
import java.util.ArrayList;

import com.itl.dao.masters.CityDAO;
import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;

public class CityDaoStub implements CityDAO {

	@Override
	public CityMst getPrimaryKey(Long id) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityMst> getByCityName(String cityName) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityMst> getByCityId(String cityId) throws NGException {
		if(cityId == null) {
			return null;
		}
		if(cityId.equals("123")) {
			return new ArrayList<>();
		} else {
			List<CityMst> cityList = new ArrayList();
			CityMst cityMst = new CityMst();
			cityMst.setCityId(cityId);
			cityMst.setCityName("Mumbai");
			cityList.add(cityMst);
			return cityList;
		}
		
	}

	@Override
	public List<CityMst> getByCountryState(String countryId, String stateId, String cityId) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityMst> getByDeleted(Boolean isDeleted) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCityIdByCityName(String cityname) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CityMst save(CityMst city) throws NGException {
		// TODO Auto-generated method stub
		return null;
	}

}
