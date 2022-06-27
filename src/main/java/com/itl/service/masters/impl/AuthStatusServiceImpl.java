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
import com.itl.dao.masters.AuthStatusDAO;
import com.itl.domain.entities.masters.AuthStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.impl.NGServiceImpl;
import com.itl.service.masters.AuthStatusService;

@Service("authStatusService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = NGException.class)
public class AuthStatusServiceImpl extends NGServiceImpl<Long, AuthStatusMst>
	implements AuthStatusService {

	private static final Logger logger = LoggerFactory.getLogger(AuthStatusServiceImpl.class);
	
	@Autowired
	protected AuthStatusDAO authStatusDAO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) authStatusDAO);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		authStatusDAO.setEntityManager(entityManager);
	}
	
	public AuthStatusMst getPrimaryKey(Long Id) throws NGException {
		AuthStatusMst opStat = authStatusDAO.getPrimaryKey(Id);
		if (null == opStat) {
			return null;
		} else {
			return opStat;
		}
	}

	public List<AuthStatusMst> getByAuthStatusName(String authStatusName) throws NGException {
		List<AuthStatusMst> opStat = authStatusDAO.getByAuthStatusName(authStatusName);
		return opStat;
	}
	
	public List<AuthStatusMst> getByAuthStatusId(String authStatusId) throws NGException {
		List<AuthStatusMst> opStat = authStatusDAO.getByAuthStatusId(authStatusId);
		return opStat;
	}
	
	public AuthStatusMst saveOrUpdate(String loginId, AuthStatusMst entity) throws NGException {
		logger.info("PK>>ID::" + entity.getId());
		AuthStatusMst rMaaz = getPrimaryKey(entity.getId());
		try {
			if (null == rMaaz) {
				return super.saveOrUpdate(loginId, entity);
			} else {
				// ---> update mode
				AuthStatusMst rmfa = rMaaz;
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
