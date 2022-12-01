package com.itl.service.masters;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itl.controller.CityController;
import com.itl.domain.entities.masters.CityMst;
import com.itl.service.masters.Impl.CityServiceImpl;
import com.itl.utils.OmniConstants;
import com.itl.web.dto.CityVO;

public class CityControllerTest {
	
	@InjectMocks
	private CityController cityController;

	@Mock
	private CityServiceImpl cityService;

	private MockMvc mvc;

	ObjectMapper objmapper = new ObjectMapper();
	ObjectWriter obhwriter = objmapper.writer();

	List<CityMst> cityList = new ArrayList<CityMst>();
	CityMst citydata = new CityMst();
	CityMst citydata2 = new CityMst();
	CityMst citydata3 = new CityMst();
	CityMst citydata4 = new CityMst();

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(cityController).build();

		citydata.setCityId("RRRR");
		citydata.setCityName("delhi");
		citydata.setStateId("Mh");
		citydata.setCountryId("Ind");
		citydata.setAuthStatus("A");
		cityList.add(citydata);

		citydata2.setCityId("PPP");
		citydata2.setCityName("Pune");
		citydata2.setStateId("MH");
		citydata2.setCountryId("Ind");
		citydata2.setAuthStatus("P");
		cityList.add(citydata2);

		citydata3.setCityId("MMM");
		citydata3.setCityName("Mumbai");
		citydata3.setStateId("MH");
		citydata3.setCountryId("Ind");
		citydata3.setAuthStatus("R");
		cityList.add(citydata3);

		citydata4.setCityId("BBB");
		citydata4.setCityName("Banglor");
		citydata4.setStateId("TT");
		citydata4.setCountryId("Ind");
		citydata4.setAuthStatus("D");
		cityList.add(citydata4);

	}

	
	@Test
	public void createCitySuccess_PassingCityIdWhichIsNotInDb() throws Exception {

		CityVO vo = new CityVO();
		vo.setCityId("RRRR");
		vo.setCityName("delhi");
		vo.setStateId("Mh");
		vo.setCountryId("Ind");
		vo.setAuthStatus("A");
		
		String content = obhwriter.writeValueAsString(vo);// convert json data into string
		
		Mockito.when(cityService.getByCityId("RRRR")).thenReturn(null);
		Mockito.when(cityService.saveOrUpdate(startsWith("log"), any())).thenReturn(citydata);
		//Mockito.when(cityService.updateCacheList(OmniConstants.AUTH_AUTHORIZED)).thenReturn(null);
		doNothing().when(cityService).updateCacheList(OmniConstants.AUTH_AUTHORIZED);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/createCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);


		mvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Successfully saved record"));

	}
	
	@Test
	public void createCityFails_WhenExceptionIsThrown() throws Exception {

		CityVO vo = new CityVO();
		vo.setCityId("RRRR");
		vo.setCityName("delhi");
		vo.setStateId("Mh");
		vo.setCountryId("Ind");
		vo.setAuthStatus("A");
		
		String content = obhwriter.writeValueAsString(vo);// convert json data into string
		
		Mockito.when(cityService.getByCityId("RRRR")).thenReturn(null);
		Mockito.when(cityService.saveOrUpdate(startsWith("log"), any())).thenThrow(NullPointerException.class);
		//Mockito.when(cityService.updateCacheList(OmniConstants.AUTH_AUTHORIZED)).thenReturn(null);
		doThrow(NullPointerException.class).when(cityService).updateCacheList(OmniConstants.AUTH_AUTHORIZED);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/createCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);


		mvc.perform(requestBuilder).andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.content().string("Failure while saving record"));

	}
	
	@Test
	public void createCitySuccess_PassingCityIdWhichIsNotInDbWithCaptor() throws Exception {

		CityVO vo = new CityVO();
		vo.setCityId("RRRR");
		vo.setCityName("delhi");
		vo.setStateId("Mh");
		vo.setCountryId("Ind");
		vo.setAuthStatus("A");
		
		String content = obhwriter.writeValueAsString(vo);// convert json data into string
		
		Mockito.when(cityService.getByCityId("RRRR")).thenReturn(null);
		
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		
		Mockito.when(cityService.saveOrUpdate(valueCapture.capture(), any())).thenReturn(citydata);
		//Mockito.when(cityService.updateCacheList(OmniConstants.AUTH_AUTHORIZED)).thenReturn(null);
		doNothing().when(cityService).updateCacheList(OmniConstants.AUTH_AUTHORIZED);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/createCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

		System.out.println(valueCapture);
		mvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Successfully saved record"));

	}

}
