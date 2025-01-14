package com.itl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
public class CityControllerTest {

	@InjectMocks
	private CityController cityController;

	@Mock
	private CityServiceImpl CityService;

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
	@Order(1)
	public void createCitySuccess_whenCityIdNotNullAndCityIdNotDuplicate() throws Exception {

		String content = obhwriter.writeValueAsString(citydata);// convert json data into string
		Mockito.when(CityService.getByCityId("RRRR")).thenReturn(null);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/createCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

		Mockito.when(CityService.saveOrUpdate(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(citydata);

		mvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Successfully saved record"));

	}

	@Test
	@Order(2)
	public void updateCityRecordSuccess_MatchwithExistingCityId() throws Exception {

		String updatedcontent = obhwriter.writeValueAsString(citydata);// convert json data into string
		Mockito.when(CityService.getByCityId("RRRR")).thenReturn(citydata);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/updateCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedcontent);

		Mockito.when(CityService.saveOrUpdate(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(citydata);

		mvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void updateCitythrowsError_whenCityIdNotMatchedwithExistingCityId() throws Exception {

		String updatedcontent = obhwriter.writeValueAsString(citydata);// convert json data into string
		Mockito.when(CityService.getByCityId(null)).thenReturn(citydata);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/updateCity")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedcontent);

		Mockito.when(CityService.saveOrUpdate(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(citydata);

		mvc.perform(requestBuilder).andExpect(status().isBadRequest());

	}

	@Test
	public void GetCityDataByGetByCityId_whenCityIdMatchwithExistingCityId() throws Exception {
		Mockito.when(CityService.getByCityId("PPP")).thenReturn(Helper());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/id/PPP")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void GetByCityId_whenCityIdisMatchwithExistingCityId() throws Exception {
		Mockito.when(CityService.getByCityId("MMM")).thenReturn(citydata3);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/id/MMM")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	public void GetCityData_whenAuthStatusAuthorized() throws Exception {
		Mockito.when(CityService.getByAuthStatus(OmniConstants.AUTH_AUTHORIZED, Boolean.FALSE))
				.thenReturn(authStatusHelper(OmniConstants.AUTH_AUTHORIZED));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/list/authorized")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void GetpendingCityData_whenAuthStatusAuthorized() throws Exception {
		Mockito.when(CityService.getByAuthStatus(OmniConstants.AUTH_PENDING, Boolean.FALSE))
				.thenReturn(authStatusHelper(OmniConstants.AUTH_PENDING));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/list/pending")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void GetRejectedgCityData_whenAuthStatusAuthorized() throws Exception {
		Mockito.when(CityService.getByAuthStatus(OmniConstants.AUTH_REJECTED, Boolean.FALSE))
				.thenReturn(authStatusHelper(OmniConstants.AUTH_REJECTED));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/list/rejected")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void GetDeletedCityData() throws Exception {
		Mockito.when(CityService.getByDeleted(Boolean.TRUE)).thenReturn(authStatusHelper(OmniConstants.AUTH_DELETED));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/City/list/deleted")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void DeleteCityById_whenCityIdisMatchwithExistingCityId() throws Exception {
		String deletedcontent = obhwriter.writeValueAsString(citydata);// convert json data into string
		Mockito.when(CityService.getByCityId("RRR")).thenReturn(citydata);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/deleteCity/RRR")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(deletedcontent);

		Mockito.when(CityService.saveOrUpdate(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(citydata);
		mvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	@Order(7)
	public void DeleteCityByIdGivesError_whenCityIdisNotMatchwithExistingCityId() throws Exception {
		String deletedcontent = obhwriter.writeValueAsString(citydata3);// convert json data into string
		Mockito.when(CityService.getByCityId(citydata3.getCityId())).thenReturn(citydata3);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/City/deleteCity/NNNN")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(deletedcontent);

		Mockito.when(CityService.saveOrUpdate(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(citydata3);
		mvc.perform(requestBuilder).andExpect(status().isNotFound());

	}

	public CityMst Helper() {
		for (CityMst s : cityList) {
			if (null != s.getCityId()) {
				return s;
			}
		}
		return null;
	}

	public List<CityMst> authStatusHelper(String authStatus) {
		List<CityMst> cityMsts = new ArrayList<>();
		for (CityMst s : cityList) {
			if (null != s.getAuthStatus() && authStatus.equals(s.getAuthStatus())) {
				cityMsts.add(s);
			}
		}
		return cityMsts;
	}
}
