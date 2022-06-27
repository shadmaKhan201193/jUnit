package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.AddressType;
import com.itl.exceptions.NGException;

public interface AddressTypeDAO extends JPADAO<AddressType, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public AddressType getPrimaryKey(Long Id) throws NGException;
	
	public List<AddressType> getByAddressTypeName(String addressTypeName) throws NGException;
	
	public List<AddressType> getByAddressTypeId(String addressTypeId) throws NGException;
	
}
