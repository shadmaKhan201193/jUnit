package com.itl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.itl.repository.CityRepository;

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
