package com.itl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itl.dao.masters.Impl.CityDAOImpl;
import com.itl.domain.entities.masters.CityMst;
import com.itl.service.masters.Impl.CityServiceImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CityServiceTest {

	@InjectMocks
	private CityServiceImpl cityServiceImpl;

	@Mock
	private CityDAOImpl cityDAOImpl;

	ObjectMapper objmapper = new ObjectMapper();
	ObjectWriter obhwriter = objmapper.writer();

	private List<CityMst> citiList;
	
	@BeforeEach
	public void setup() {
		CityMst citydata = new CityMst();
		citydata.setCityId("DDD");
		citydata.setCityName("Pune");
		citydata.setStateId("Mh");
		citydata.setCountryId("Ind");
		citydata.setAuthStatus("A");
	}
	
	

	@Test
	public void getByCityId() {
		String cityId = "DDD";
		CityMst citydata = new CityMst();
		CityDAOImpl cs = mock(cityDAOImpl.getClass());
		when(cityDAOImpl.getByCityId(cityId)).thenReturn(Arrays.asList());
	}
	
//
//	@Test
//	public void getByCityName() {
//		String cityname = "Pune";
//		CityMst citydata = new CityMst();
//		CityDAOImpl cs = mock(cityDAOImpl.getClass());
//		when(cityDAOImpl.getByCityName(cityname)).thenReturn(Arrays.asList());
//	}

//	@Test
//	public void getPrimaryKey() {
//		Long id = (long) 1234;
//		CityMst citydata = new CityMst();
//		CityDAOImpl cs = mock(cityDAOImpl.getClass());
//		assertThat(cityDAOImpl.getPrimaryKey(citydata.getId())).isEqualTo(id);
//	}
	
	@Test
	public void getCityIdByCityName() {
		CityMst citydata = new CityMst();
		citydata.setCityId("DDD");
		CityDAOImpl cs = mock(cityDAOImpl.getClass());
		String cityname="Pune";
		when(cityDAOImpl.getCityIdByCityName(citydata.getCityName())).thenReturn(cityname);
	}
	@Test
	public void updateCacheList_getByAuthStatus() {
		CityMst citydata = new CityMst();
		CityDAOImpl cs = mock(cityDAOImpl.getClass());
		Boolean isDeleted = null;
		String authStatus = "A";
		when(cityDAOImpl.getByAuthStatus(authStatus, isDeleted)).thenReturn(Arrays.asList());

	}
	
	@Test
	public void getByDeleted() {
		CityMst citydata = new CityMst();
		CityDAOImpl cs = mock(cityDAOImpl.getClass());
	
		Boolean isDeleted=true;
		when(cityDAOImpl.getByDeleted(isDeleted)).thenReturn(Arrays.asList());
	}
	
	
	
	
}
