package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.AddressType;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface AddressTypeService extends NGService {

	public AddressType getPrimaryKey(Long Id) throws NGException;

	public List<AddressType> getByAddressTypeName(String addressTypeName) throws NGException;
	
	public List<AddressType> getByAddressTypeId(String addressTypeId) throws NGException;
	
	public AddressType saveOrUpdate(String loginId, AddressType entity) throws NGException;
}
