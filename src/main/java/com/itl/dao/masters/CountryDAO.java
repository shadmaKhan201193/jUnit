package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.CountryMst;
import com.itl.exceptions.NGException;

public interface CountryDAO extends JPADAO<CountryMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public CountryMst getPrimaryKey(Long Id) throws NGException;
	
	public List<CountryMst> getByCountryName(String countryName) throws NGException;
	
	public List<CountryMst> getByCountryId(String countryId) throws NGException;
	
	public List<CountryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<CountryMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getCountryIdByCountryName(String countryName) throws NGException;
	
}
