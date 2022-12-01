package com.itl.service.masters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import com.itl.dao.masters.CityDAO;
import com.itl.dao.masters.Impl.CityDAOImpl;
import com.itl.domain.entities.masters.CityMst;
import com.itl.exceptions.NGException;
import com.itl.service.masters.Impl.CityServiceImpl;

public class CityServiceTest02 {

	@Mock
	CityDAO cityDAO;
	
	@InjectMocks
	CityServiceImpl serviceImpl;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testCityById_passingNullId() {
		CityMst mst = serviceImpl.getByCityId(null);
		assertNull(mst);
	}
	
	@Test
	public void testCityById_passingValidId() {
		
		List<CityMst> cityList = new ArrayList<CityMst>();
		CityMst citydata = new CityMst();
		citydata.setCityId("MUM");
		citydata.setCityName("Mumbai");
		cityList.add(citydata);
		
		
		CityDAO mockCityDao = spy(new CityDAOImpl());
		try {
			Object x = Whitebox.invokeMethod(mockCityDao, "getByCityId", "123");
			System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CityMst mst = serviceImpl.getByCityId("123");
		assertNull(mst);
		//assertEquals(mst.getCityId(), "MUM");
	}
}
