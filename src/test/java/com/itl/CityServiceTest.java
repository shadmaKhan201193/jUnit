package com.itl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itl.dao.masters.Impl.CityDAOImpl;
import com.itl.domain.entities.masters.CityMst;
import com.itl.service.masters.Impl.CityServiceImpl;

@ExtendWith(SpringExtension.class)
public class CityServiceTest {

	/**
	 * Instantiate and setup the object under test
	 */
	@InjectMocks
	private CityServiceImpl cityServiceImpl;

	/**
	 * Mock the autowired object(s)
	 */
	@Mock
	private CityDAOImpl cityDAOImpl;
	
	/**
	 * Create a bunch of data which can be used during the test execution and setup
	 * its values etc. in the setup method.
	 */
	List<CityMst> cityList = new ArrayList<CityMst>();
	CityMst citydata = new CityMst();
	CityMst citydata2 = new CityMst();
	CityMst citydata3 = new CityMst();
	CityMst citydata4 = new CityMst();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		citydata.setCityId("111");
		citydata.setCityName("delhi");
		citydata.setStateId("DL");
		citydata.setCountryId("IN");
		citydata.setAuthStatus("A");
		cityList.add(citydata);

		citydata2.setCityId("222");
		citydata2.setCityName("Pune");
		citydata2.setStateId("MH");
		citydata2.setCountryId("IN");
		citydata2.setAuthStatus("A");
		cityList.add(citydata2);

		citydata3.setCityId("333");
		citydata3.setCityName("Mumbai");
		citydata3.setStateId("MH");
		citydata3.setCountryId("IN");
		citydata3.setAuthStatus("A");
		cityList.add(citydata3);

		citydata4.setCityId("444");
		citydata4.setCityName("Bangalore");
		citydata4.setStateId("KA");
		citydata4.setCountryId("IN");
		citydata4.setAuthStatus("P");
		cityList.add(citydata4);
		
		
		/**
		 * Here, all the mock behaviors are defined in the common setup function, these
		 * can also be defined in the individual test cases depending on the need.
		 * 
		 * Mocking is deciding the behavior of how things will work in real world. It
		 * also helps avoid execution control going out of the function under test.
		 */
		when(cityDAOImpl.getByCityId(null)).thenReturn(null);
		when(cityDAOImpl.getByCityId("111")).thenReturn(cityList.stream().filter(c -> c.getCityId().equals("111")).toList());
		when(cityDAOImpl.getByCityId("222")).thenReturn(cityList.stream().filter(c -> c.getCityId().equals("222")).toList());
		when(cityDAOImpl.getByCityId("333")).thenReturn(cityList.stream().filter(c -> c.getCityId().equals("333")).toList());
		when(cityDAOImpl.getByCityId("444")).thenReturn(cityList.stream().filter(c -> c.getCityId().equals("444")).toList());
		when(cityDAOImpl.getByCityId("999")).thenReturn(cityList.stream().filter(c -> c.getCityId().equals("999")).toList());
	}
	
	

	/**
	 * Test case to check the behavior when sending null ID. The actual function
	 * should handle null input and not throw unnecessary exception (this is subject
	 * to change as per business need).
	 */
	@Test
	public void getByCityId_whenSendingNullId() {
		String cityId = null;
		CityMst city = cityServiceImpl.getByCityId(cityId);
		assertNull(city);
		
	}
	
	/**
	 * Test case to check the behavior when sending ID which does not exist in DB
	 */
	@Test
	public void getByCityId_whenSendingNonExistentId() {
		String cityId = "999";
		CityMst city = cityServiceImpl.getByCityId(cityId);
		assertNull(city);
	}
	
	/**
	 * Test case to check the behavior when sending ID which exist in DB
	 */
	@Test
	public void getByCityId_whenSendingExistingId() {
		String cityId = "111";
		CityMst city = cityServiceImpl.getByCityId(cityId);
		assertNotNull(city);
		assertEquals("111", city.getCityId());
	}
	
}
