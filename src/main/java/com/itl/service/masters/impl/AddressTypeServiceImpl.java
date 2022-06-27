package com.itl.service.masters.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.dao.base.JPADAO;
import com.itl.dao.masters.AddressTypeDAO;
import com.itl.domain.entities.masters.AddressType;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.AddressTypeService;

@Service("addressTypeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class AddressTypeServiceImpl extends NGServiceImpl<Long, AddressType>
	implements AddressTypeService {

	private static final Logger logger = LoggerFactory.getLogger(AddressTypeServiceImpl.class);
	
	@Autowired
	protected AddressTypeDAO addressTypeDAO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) addressTypeDAO);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		addressTypeDAO.setEntityManager(entityManager);
	}
	
	public AddressType getPrimaryKey(Long Id) throws NGException {
		AddressType opStat = addressTypeDAO.getPrimaryKey(Id);
		if (null == opStat) {
			return null;
		} else {
			return opStat;
		}
	}

	public List<AddressType> getByAddressTypeName(String addressTypeName) throws NGException {
		List<AddressType> opStat = addressTypeDAO.getByAddressTypeName(addressTypeName);
		return opStat;
	}
	
	public List<AddressType> getByAddressTypeId(String addressTypeId) throws NGException {
		List<AddressType> opStat = addressTypeDAO.getByAddressTypeId(addressTypeId);
		return opStat;
	}
	
	public AddressType saveOrUpdate(String loginId, AddressType entity) throws NGException {
		logger.info("PK>>ID::" + entity.getId());
		AddressType rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				return super.saveOrUpdate(loginId, entity);
			} else {
				// ---> update mode
				AddressType rmfa = rMaaz;
				Mapper mapper = new DozerBeanMapper();
				mapper.map(entity, rmfa);
				return super.saveOrUpdate(loginId, rmfa);
			}
		} catch (Exception e) {
			logger.info("Error for PK");
			e.printStackTrace();
			logger.info("Error for PK>>ID::" + entity.getId());
		}
		return null;
	}
}
