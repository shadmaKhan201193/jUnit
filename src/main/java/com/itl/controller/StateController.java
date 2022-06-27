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
import com.itl.domain.entities.masters.StateMst;
import com.itl.service.masters.StateService;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.StateVO;
import com.itl.web.dto.StateVOList;

@RestController
@RequestMapping("/State")
public class StateController {
	
	private static final Logger logger = LoggerFactory.getLogger(StateController.class);
	@Autowired
	private StateService StateService;
	@PostMapping(value = "/createState", consumes = "application/json", produces = "application/json")
    public String createState(@RequestBody StateVO State) {
		logger.debug("Add State for stateId : {}" + State.getStateId());
		StateMst stateMst = StateService.getByStateId(State.getStateId());
		if (null != stateMst) {
			logger.info("State with stateId {} already exists.", State.getStateId());
			return "Failure.. Record already exists";
		} else {
			stateMst = new StateMst();
			Date dt = new Date();
			stateMst.setCreatedBy("login");
			stateMst.setCreatedDate(dt);
			stateMst.setCreatedTime(dt);
			stateMst.setLastModifiedBy("login");
			stateMst.setLastModifiedDate(dt);
			stateMst.setLastModifiedTime(dt);
			stateMst.setAuthStatus(OmniConstants.AUTH_AUTHORIZED);
			stateMst.setIsActive(1);
			stateMst.setIsDeleted(Boolean.FALSE);
			stateMst.setStateId(stateMst.getStateId());
			stateMst.setStateName(stateMst.getStateName());
			stateMst.setStateDisplayName(stateMst.getStateDisplayName());
			stateMst.setCountryId(stateMst.getCountryId());

			StateMst stateMstNew = StateService.saveOrUpdate("login", stateMst);
			if (null != stateMstNew) {
				StateService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);
				logger.info("State & Cache - {} - saved Successfully", State.getStateId());
				return "Successfully saved record";
			} else {
				logger.info("Failed to save State - {}", State.getStateId());
				return "Failure while saving record";
			}
		}
	}
	
	
	@PostMapping(value = "/updateState", consumes = "application/json", produces = "application/json")
    public String updateState(@RequestBody StateVO state) {
		logger.debug("Updating State with StateId : {}" + state.getStateId());
		StateMst stateMst = StateService.getByStateId(state.getStateId());
		if (null != stateMst) {
			Date dt = new Date();
			stateMst.setLastModifiedBy("login2");
			stateMst.setLastModifiedDate(dt);
			stateMst.setLastModifiedTime(dt);
			stateMst.setAuthStatus(stateMst.getAuthStatus());
			//stateMst.setIsActive(Integer.parseInt(stateMst.getIsActive()));
			stateMst.setIsActive(stateMst.getIsActive());
			stateMst.setIsDeleted(stateMst.getIsDeleted().equals("1") ? true : false);
			stateMst.setStateId(stateMst.getStateId());
			stateMst.setStateName(stateMst.getStateName());
			stateMst.setStateDisplayName(stateMst.getStateDisplayName());
			stateMst.setCountryId(stateMst.getCountryId());

			StateMst stateMstNew = StateService.saveOrUpdate("login", stateMst);
			if (null != stateMstNew) {
				StateService.updateCacheList(OmniConstants.AUTH_AUTHORIZED);			
				logger.info("State & Cache updated successfully for {}", state.getStateId());
				return "Successfully updated Record";
			} else {
				logger.info("Failure to update State with id : {}", state.getStateId());
				return "Failure while updating record";
			}
		} else {
			return "Failure.. Record does not exists";
		}
	}

	@GetMapping(value = "/id/{id}", produces = "application/json")
	public StateVO getStateById(@PathVariable String id) {
		logger.info("Fetch State By StateId : {}", id);
		StateMst stateMst = StateService.getByStateId(id);
		StateVO state = null;
		if (null != stateMst) {
			state = new StateVO();
			state.setStateId(stateMst.getStateId());
			state.setStateName(stateMst.getStateName());
			state.setStateDisplayName(stateMst.getStateDisplayName());
			state.setCountryId(stateMst.getCountryId());
			state.setAuthStatus(stateMst.getAuthStatus());
			state.setIsActive(Integer.toString(stateMst.getIsActive()));
			state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
		}
		//TODO - add else part to reply that the state is not found
		return state;
	}

	
	@GetMapping(value = "/name/{name}", produces = "application/json")
	public StateVO getStateByName(@PathVariable String name) {
		logger.info("Fetch State by StateName : {}", name);
		String stateId = StateService.getStateIdByStateName(name);
		StateMst stateMst = StateService.getByStateId(stateId);
		StateVO state = null;
		if (null != stateMst) {
			state = new StateVO();
			state.setStateId(stateMst.getStateId());
			state.setStateName(stateMst.getStateName());
			state.setStateDisplayName(stateMst.getStateDisplayName());
			state.setCountryId(stateMst.getCountryId());
			state.setAuthStatus(stateMst.getAuthStatus());
			state.setIsActive(Integer.toString(stateMst.getIsActive()));
			state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
		}
		//TODO - add else part to reply state not found
		return state;
	}
	
	@GetMapping(value = "/list/authorized", produces = "application/json")
    public StateVOList getStateByAuthorizedStatus() { 
		logger.info("Fetch State by AuthStatus : A");
		List<StateMst> stateList = StateService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED,Boolean.FALSE);
		StateVOList voList = new StateVOList();
		List<StateVO> stateVOList = new ArrayList<StateVO>(); 
		if (null != stateList) {
			for (StateMst stateMst: stateList) {
				logger.debug("Inside for loop");
				StateVO state = new StateVO();
				state.setStateId(stateMst.getStateId());
				state.setStateName(stateMst.getStateName());
				state.setStateDisplayName(stateMst.getStateDisplayName());
				state.setCountryId(stateMst.getCountryId());
				state.setAuthStatus(stateMst.getAuthStatus());
				state.setIsActive(Integer.toString(stateMst.getIsActive()));				
				state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
				stateVOList.add(state);
			}
		}
		voList.setState(stateVOList);
		logger.debug("Exit from method");
        return voList; 
    } 
	
	
	@GetMapping(value = "/list/pending", produces = "application/json")
    public StateVOList getStateByPendingStatus() { 
		logger.info("Fetch State by AuthStatus : P");
		List<StateMst> stateList = StateService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE);
		logger.info("After Service call");
		StateVOList voList = new StateVOList();
		List<StateVO> stateVOList = new ArrayList<StateVO>(); 
		logger.info("Before if condition " +stateList);
		if (null != stateList) {
			for (StateMst stateMst: stateList) {
				logger.debug("Inside for loop");
				StateVO state = new StateVO();
				state.setStateId(stateMst.getStateId());
				state.setStateName(stateMst.getStateName());
				state.setStateDisplayName(stateMst.getStateDisplayName());
				state.setCountryId(stateMst.getCountryId());
				state.setAuthStatus(stateMst.getAuthStatus());
				state.setIsActive(Integer.toString(stateMst.getIsActive()));				
				state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
				stateVOList.add(state);
			}
		}
		voList.setState(stateVOList);
		logger.debug("Exit from method");
        return voList; 
    } 
	
	
	
	@GetMapping(value = "/list/rejected", produces = "application/json")
    public StateVOList getStateByRejectedStatus() { 
		logger.info("Fetch State by AuthStatus : R");
		List<StateMst> stateList = StateService.getByAuthStatus(OmniConstants.AUTH_REJECTED,Boolean.FALSE);
		logger.info("After Service call");
		StateVOList voList = new StateVOList();
		List<StateVO> stateVOList = new ArrayList<StateVO>(); 
		if (null != stateList) {	
			for (StateMst stateMst: stateList) {
				logger.debug("Inside for loop");
				StateVO state = new StateVO();
				state.setStateId(stateMst.getStateId());
				state.setStateName(stateMst.getStateName());
				state.setStateDisplayName(stateMst.getStateDisplayName());
				state.setCountryId(stateMst.getCountryId());
				state.setAuthStatus(stateMst.getAuthStatus());
				state.setIsActive(Integer.toString(stateMst.getIsActive()));				
				state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
				logger.debug("Before adding state in a List ");
				stateVOList.add(state);
			}
		}
		voList.setState(stateVOList);
		logger.debug("Exit from method");
        return voList; 
    } 
	
	
	
	
	@GetMapping(value = "/list/deleted", produces = "application/json")
    public StateVOList getStateByDeleted() { 
		logger.info("Fetch State by AuthStatus : D");
		List<StateMst> stateList = StateService.getByDeleted(Boolean.TRUE); 
		logger.info("After Service call");
		StateVOList voList = new StateVOList();
		List<StateVO> stateVOList = new ArrayList<StateVO>();
		if (null != stateList) {
			for (StateMst stateMst: stateList) {
				logger.debug("Inside for loop");
				StateVO state = new StateVO();
				state.setStateId(stateMst.getStateId());
				state.setStateName(stateMst.getStateName());
				state.setStateDisplayName(stateMst.getStateDisplayName());
				state.setCountryId(stateMst.getCountryId());
				state.setAuthStatus(stateMst.getAuthStatus());
				state.setIsActive(Integer.toString(stateMst.getIsActive()));				
				state.setIsDeleted(String.valueOf(stateMst.getIsDeleted()));
				stateVOList.add(state);
			}
		}
		voList.setState(stateVOList);
		logger.debug("Exit from method");
        return voList;
    }
	
	
	@PostMapping(value = "/deleteState/{id}", consumes = "application/json", produces = "application/json")
    public String deleteState(@PathVariable(name="id") String stateId) {
		logger.debug("Delete State by stateId : {}", stateId);
		StateMst stateMst = StateService.getByStateId(stateId);
		if (null != stateMst) {
			logger.debug("Inside if condition");
			
			Date dt = new Date();
			stateMst.setDeletedBy("login3"); 
			stateMst.setDeletedDate(dt);
			stateMst.setDeletedTime(dt);
		    stateMst.setAuthStatus("D");		
		    stateMst.setIsActive(0);
			stateMst.setIsDeleted(Boolean.TRUE);
			
			StateMst stateMstNew = StateService.saveOrUpdate("login", stateMst);
			logger.debug("After saveOrUpdate call" );
			if (null != stateMstNew) {
				logger.debug("Inside if Condition ..record deleted Successfully");
				return "Successfully deleted record";
			} else {
				logger.debug("Inside else Condition ..Failure while deleting record");
				return "Failure while deleting record";
			}
		} else {
			logger.debug("Inside else Condition ..Failure.. Record does not exists");
			return "Failure.. Record does not exists";
		}
    }
	
}
