package com.itl.service.masters;

import java.util.List;
import com.itl.domain.entities.masters.OrgTypeMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface OrgTypeService extends NGService {

	public OrgTypeMst getPrimaryKey(Long Id) throws NGException ;
	
	public OrgTypeMst getByOrgTypeId(String orgTypeId) throws NGException;
	
	public OrgTypeMst saveOrUpdate(String loginId, OrgTypeMst entity) throws NGException;

	public List<OrgTypeMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<OrgTypeMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<OrgTypeMst> updateCacheList(String authStatus) throws NGException;

	public String getOrgTypeIdByOrgTypeName(String orgTypeName)throws NGException;
	
}
