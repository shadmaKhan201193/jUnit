package com.itl;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.itl.repository.CityRepository;
import com.itl.service.masters.CityService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class MasterServiceApplicationTests {
//	@Autowired
//	private CityService CityService;

	@MockBean
	private CityRepository cityRepo;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void saveOrUpdate(String loginId, CityMst entity) {
//		CityMst citymst = new CityMst();
//		when(cityRepo.save(citymst)).thenReturn(citymst);
//		assertEquals(citymst, CityService.saveOrUpdate(loginId, entity));
//	}
		

}
