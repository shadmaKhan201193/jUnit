package com.itl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itl.dao.masters.Impl.CityDAOImpl;
import com.itl.domain.entities.masters.CityMst;
import com.itl.repository.CityRepository;

@ExtendWith(SpringExtension.class)
public class CityDaoImplTest {

	@InjectMocks
	private CityDAOImpl cityDAOImpl;
	@Mock
	private CityRepository CityRepo;

	ObjectMapper objmapper = new ObjectMapper();
	ObjectWriter obhwriter = objmapper.writer();
	
	@BeforeEach
	public void setUp() {
		List<CityMst> cityList = new ArrayList<CityMst>();
		CityMst citydata = new CityMst();
		citydata.setCityId("DDD");
		citydata.setCityName("delhi");
		citydata.setStateId("Mh");
		citydata.setCountryId("Ind");
		citydata.setAuthStatus("A");
		cityList.add(citydata);

		CityMst citydata2 = new CityMst();
		citydata2.setCityId("PPP");
		citydata2.setCityName("Pune");
		citydata2.setStateId("MH");
		citydata2.setCountryId("Ind");
		citydata2.setAuthStatus("P");
		cityList.add(citydata2);

		CityMst citydata3 = new CityMst();
		citydata3.setCityId("MMM");
		citydata3.setCityName("Mumbai");
		citydata3.setStateId("MH");
		citydata3.setCountryId("Ind");
		citydata3.setAuthStatus("R");
		cityList.add(citydata3);
		Mockito.when(CityRepo.save(Mockito.any())).thenReturn(citydata3);
	}

	public void getByCityId() {
		List<CityMst> cityList = new ArrayList<CityMst>();
		CityMst citydata = new CityMst();
		citydata.setCityId("DDD");
		citydata.setCityName("delhi");
		citydata.setStateId("Mh");
		citydata.setCountryId("Ind");
		citydata.setAuthStatus("A");
		
		
		cityList.add(citydata);

		
		
	}

}
