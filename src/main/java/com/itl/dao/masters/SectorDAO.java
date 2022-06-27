package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.SectorMst;
import com.itl.exceptions.NGException;

public interface SectorDAO extends JPADAO<SectorMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public SectorMst getPrimaryKey(Long Id) throws NGException;
	
	public List<SectorMst> getBySectorName(String sectorName) throws NGException;
	
	public List<SectorMst> getBySectorId(String sectorId) throws NGException;

	public List<SectorMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<SectorMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getSectorIdBySectorName(String sectorName) throws NGException;
	
}
