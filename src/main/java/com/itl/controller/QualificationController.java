package com.itl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itl.domain.entities.masters.QualificationMst;

import com.itl.service.masters.QualificationService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.QualificationVO;
import com.itl.web.dto.QualificationVOList;


@RestController
@RequestMapping("/Qualification")
public class QualificationController {

private static final Logger logger = LoggerFactory.getLogger(QualificationController.class);
	
	@Autowired
	private QualificationService QualificationService;
	
	@PostMapping(value = "/createQualification", consumes = "application/json", produces = "application/json")
    public String createQualification(@RequestBody QualificationVO qualification) {
		logger.debug("Add qualification for qualificationId : {}" + qualification.getQualificationId());
		QualificationMst qualificationMst = QualificationService.getByQualificationId(qualification.getQualificationId());
		
		if (null != qualificationMst) {
			logger.debug(" qualification with qualificationId  already exists" + qualification.getQualificationId());
			return "Failure.. Record already exists";
		} else {
            qualificationMst = new QualificationMst();
			Date dt = new Date();
			qualificationMst.setCreatedBy("login");
			qualificationMst.setCreatedDate(dt);
			qualificationMst.setCreatedTime(dt);
			qualificationMst.setLastModifiedBy("login");
			qualificationMst.setLastModifiedDate(dt);
			qualificationMst.setLastModifiedTime(dt);			
			qualificationMst.setAuthStatus("A");
			qualificationMst.setIsActive(1);
			qualificationMst.setIsDeleted(Boolean.FALSE);
			qualificationMst.setQualificationId(qualification.getQualificationId());
			qualificationMst.setQualificationName(qualification.getQualificationName());
			qualificationMst.setQualificationDisplayName(qualification.getQualificationDisplayName());
			
			
			QualificationMst qualificationMstNew = QualificationService.saveOrUpdate("login", qualificationMst);
			if (null != qualificationMstNew) {
				QualificationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.debug("qualification & chache save succesfully" + qualification.getQualificationId());
				return "Successfully saved record";
			} else {
				logger.debug("Failed to save qualification - {}" + qualification.getQualificationId());
				return "Failure while saving record";
			}
		}
    }
	
	@PostMapping(value = "/updateQualification", consumes = "application/json", produces = "application/json")
    public String updateQualification(@RequestBody QualificationVO qualification) {
		logger.debug("Updating qualification with qualificationId : {}" + qualification.getQualificationId());
		QualificationMst qualificationMst = QualificationService.getByQualificationId(qualification.getQualificationId());
		
		if (null != qualificationMst) {
			Date dt = new Date();
			qualificationMst.setLastModifiedBy("login2");
			qualificationMst.setLastModifiedDate(dt); 
			qualificationMst.setLastModifiedTime(dt);			
			qualificationMst.setAuthStatus(qualification.getAuthStatus());
			qualificationMst.setIsActive(Integer.parseInt(qualification.getIsActive()));
			qualificationMst.setIsDeleted(qualification.getIsDeleted().equals("1")?true:false);							
			qualificationMst.setQualificationId(qualification.getQualificationId());
			qualificationMst.setQualificationName(qualification.getQualificationName());
			qualificationMst.setQualificationDisplayName(qualification.getQualificationDisplayName());
			QualificationMst qualificationMstNew = QualificationService.saveOrUpdate("login", qualificationMst);
			
			if (null != qualificationMstNew) {
				QualificationService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.debug("qualification & Cache updated successfully for {}" + qualification.getQualificationId());
				return "Successfully updated Record";
			} else {
				logger.debug("failed to update qualification " + qualification.getQualificationId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
    }
	

	
	
	@GetMapping(value = "/id/{id}", produces = "application/json")
    public QualificationVO getQualificationById(@PathVariable String id) {
		logger.info("Fetch qualification by qualificationId : {}", id);
		QualificationMst qualificationMst = QualificationService.getByQualificationId(id);
		QualificationVO qualification = null;
		if (null != qualificationMst) {
			qualification = new QualificationVO();
			qualification.setQualificationId(qualificationMst.getQualificationId());
			qualification.setQualificationName(qualificationMst.getQualificationName());
			qualification.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
			
			qualification.setAuthStatus(qualificationMst.getAuthStatus());
			qualification.setIsActive(Integer.toString(qualificationMst.getIsActive()));				
			qualification.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
		}
        return qualification;
    }
	
	

	@GetMapping(value = "/name/{name}", produces = "application/json")
    public QualificationVO getQualificationByName(@PathVariable String name) {
		logger.info("Fetch qualification by qualificationName : {}", name);
		String qualificationId = QualificationService.getQualificationIdByQualificationName(name);
		QualificationMst qualificationMst = QualificationService.getByQualificationId(qualificationId);
		QualificationVO qualification = null;
		if (null != qualificationMst) {
			qualification = new QualificationVO();
			qualification.setQualificationId(qualificationMst.getQualificationId());
			qualification.setQualificationName(qualificationMst.getQualificationName());
			qualification.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
			
			qualification.setAuthStatus(qualificationMst.getAuthStatus());
			qualification.setIsActive(Integer.toString(qualificationMst.getIsActive()));				
			qualification.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
		}
        return qualification;
    }
	

	
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public QualificationVOList getQualificationByAuthorizedStatus() { 
		logger.info("Fetch qualification by AuthStatus : A");
		List<QualificationMst> qualificationList = QualificationService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, Boolean.FALSE);
		QualificationVOList voList = new QualificationVOList();
		List<QualificationVO> qualificationVOList = new ArrayList<QualificationVO>();  
		
		if (null != qualificationList) {
			for (QualificationMst qualificationMst: qualificationList) {
				QualificationVO qualification = new QualificationVO();
				qualification.setQualificationId(qualificationMst.getQualificationId());
				qualification.setQualificationName(qualificationMst.getQualificationName());
				qualification.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
				
				qualification.setAuthStatus(qualificationMst.getAuthStatus());
				qualification.setIsActive(Integer.toString(qualificationMst.getIsActive()));				
				qualification.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
								
				qualificationVOList.add(qualification);
			}
		}
		voList.setQualification(qualificationVOList);
        return voList;  
    }
	
	
	

	@GetMapping(value = "/list/pending", produces = "application/json")
    public QualificationVOList getQualificationByPendingStatus() { 
		logger.info("Fetch qualification by AuthStatus : P");
		List<QualificationMst> qualificationList = QualificationService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		QualificationVOList voList = new QualificationVOList();
		List<QualificationVO> qualificationVOList = new ArrayList<QualificationVO>();  
		
		if (null != qualificationList) {
			for (QualificationMst qualificationMst: qualificationList) {
				QualificationVO qualificationvo = new QualificationVO();
				qualificationvo.setQualificationId(qualificationMst.getQualificationId());
				qualificationvo.setQualificationName(qualificationMst.getQualificationName());
				qualificationvo.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
				
				qualificationvo.setAuthStatus(qualificationMst.getAuthStatus());
				qualificationvo.setIsActive(Integer.toString(qualificationMst.getIsActive()));
					
				qualificationvo.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
				
				qualificationVOList.add(qualificationvo);
			}
		}
		voList.setQualification(qualificationVOList);
        return voList;  
    }

	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public QualificationVOList getQualificationByRejectedStatus() { 
		logger.info("Fetch qualification by AuthStatus : R");
		List<QualificationMst> qualificationList = QualificationService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		QualificationVOList voList = new QualificationVOList();
		List<QualificationVO> qualificationVOList = new ArrayList<QualificationVO>();  
		
		if (null != qualificationList) {
			for (QualificationMst qualificationMst: qualificationList) {
				QualificationVO qualification = new QualificationVO();
				qualification.setQualificationId(qualificationMst.getQualificationId());
				qualification.setQualificationName(qualificationMst.getQualificationName());
				qualification.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
				
				qualification.setAuthStatus(qualificationMst.getAuthStatus());
				qualification.setIsActive(Integer.toString(qualificationMst.getIsActive()));					
				qualification.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
				
				qualificationVOList.add(qualification);
			}
		}
		voList.setQualification(qualificationVOList);
        return voList;  
    }



	@GetMapping(value = "/list/deleted", produces = "application/json")
    public QualificationVOList getQualificationByDeleted() { 
		logger.info("Fetch qualification by AuthStatus : D");
		List<QualificationMst> qualificationList = QualificationService.getByDeleted(Boolean.TRUE);
		QualificationVOList voList = new QualificationVOList();
		List<QualificationVO> qualificationVOList = new ArrayList<QualificationVO>();  
		
		if (null != qualificationList) {
			for (QualificationMst qualificationMst: qualificationList) {
				QualificationVO qualification = new QualificationVO();
				qualification.setQualificationId(qualificationMst.getQualificationId());
				qualification.setQualificationName(qualificationMst.getQualificationName());
				qualification.setQualificationDisplayName(qualificationMst.getQualificationDisplayName());
				
				qualification.setAuthStatus(qualificationMst.getAuthStatus());
				qualification.setIsActive(Integer.toString(qualificationMst.getIsActive()));					
				qualification.setIsDeleted(String.valueOf(qualificationMst.getIsDeleted()));
				qualificationVOList.add(qualification);
			}
		}
		voList.setQualification(qualificationVOList);
        return voList;  
    }
	

	@PostMapping(value = "/deleteQualification/{id}", consumes = "application/json", produces = "application/json")
    public String deleteQualification(@PathVariable(name="id") String qualificationId) {
		logger.debug("Delete qualification by qualificationId : {}", qualificationId);
		QualificationMst qualificationMst = QualificationService.getByQualificationId(qualificationId);
		
		if (null != qualificationMst) {
			Date dt = new Date();
			qualificationMst.setDeletedBy("login3");
			qualificationMst.setDeletedDate(dt);
			qualificationMst.setDeletedTime(dt);
			qualificationMst.setAuthStatus("D");
			qualificationMst.setIsActive(0);
			qualificationMst.setIsDeleted(Boolean.TRUE);
			
			QualificationMst qualificationMstNew = QualificationService.saveOrUpdate("login", qualificationMst);
			if (null != qualificationMstNew) {
				logger.info("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.info("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Exit from deleteQualification  method");
			return "Failure.. Record does not exists";
		}
    }
	
	
	
	
}
