package com.itl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.domain.entities.masters.CityMst;

@Repository
public interface CityRepository extends JpaRepository<CityMst, Long> {

	public CityMst findByCityId(Long Id);

	public List<CityMst> findByCityId(String Id);

	public List<CityMst> findByAuthStatusAndIsDeleted(String authStatus, Boolean isDeleted);

	public List<CityMst> findByCountryIdAndStateIdAndCityId(String countryId, String stateId, String cityId);

	public List<CityMst> findByIsDeleted(Boolean isDeleted);

	public List<CityMst> findByCityName(String cityName);

	 
}
