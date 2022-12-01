package com.itl.service.masters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.itl.dao.masters.Impl.CityDaoStub;
import com.itl.domain.entities.masters.CityMst;
import com.itl.service.masters.Impl.CityServiceImpl;

public class CityServiceTest {

	CityServiceImpl cityImpl;
	
	@BeforeEach
	public void setup() {
		cityImpl = new CityServiceImpl();
		CityDaoStub stub = new CityDaoStub();
		ReflectionTestUtils.setField(cityImpl, "cityDAO", stub, null);
	}
	
	@Test
	public void testGetCityById_passingValidCityId() {
		CityMst cityMst = cityImpl.getByCityId("MUM");
		//assert city not null
		//assert cityId = MUM
		assertNotNull(cityMst);
		assertEquals(cityMst.getCityId(), "MUM");
		
		cityMst = cityImpl.getByCityId(null);
		assertNull(cityMst);
		
		cityMst = cityImpl.getByCityId("123");
		assertNull(cityMst);
	}
	
}
