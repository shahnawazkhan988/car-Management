package com.cars.cars.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.http.MediaType;
import static java.util.Arrays.asList;
import org.hamcrest.core.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.cars.cars.entity.CarEntity;
import com.cars.cars.requestmodel.CarRequestModel;
import com.cars.cars.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest 
{
	
	@Mock
	BindingResult bindingResult;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CarService carService;
	
	@Test
	void testGetEmpty() throws Exception 
	{
		this.mockMvc.perform(get("/api/all/car")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}
	
	
	@Test
	void testGetNotEmpty() throws Exception 
	{
		// when
		when(carService.getAllCar())
		// then
		.thenReturn(asList( new CarRequestModel(1L, "toyota", "5000")));
		
		this.mockMvc.perform(get("/api/all/car")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("toyota")))
				.andExpect(jsonPath("$[0].price", is("5000")));
	}
	
	
	
	@Test
	void testAddNewCar200() throws Exception 
	{
		// given
		CarEntity car = new CarEntity(null,"toyota", "5000");
		when(carService.insertNewCar(any(CarRequestModel.class))).thenReturn(new CarRequestModel(1L, "toyota", "5000"));
		
		//then
		this.mockMvc.perform(post("/api/new/car")
				.content(asJsonString(car))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", equalTo("toyota")))
		.andExpect(jsonPath("$.price", equalTo("5000")));
	}
	
	
	@Test
	void testAddNewCar400() throws Exception {
		
		// given
		CarEntity car = new CarEntity(null,"toyotaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "5000");
		when(carService.insertNewCar(any(CarRequestModel.class))).thenReturn(new CarRequestModel(1L, "toyotaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "5000"));

		// When
		mockMvc.perform(post("/api/new/car").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(car))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name", Is.is("Please write car name in between 20 alphabet")));

	}
	
	@Test
	void testUpdateCar() throws Exception
	{
		//given		
		given(carService.updateCar(anyLong(), any(CarRequestModel.class))).willReturn(new  CarRequestModel(1L,"toyota", "5000"));		
		//then
		mockMvc.perform(put("/api/car/1")
		.contentType(MediaType.APPLICATION_JSON)
		.content(asJsonString(new  CarEntity(1L,"toyota", "5000"))))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo("toyota")))
		.andExpect(jsonPath("$.price", equalTo("5000")));
		then(carService).should().updateCar(anyLong(), any(CarRequestModel.class));
		then(carService).shouldHaveNoMoreInteractions();
		
	}
	
	@Test
	void testDeleteCarOK() throws Exception{
	
		mockMvc.perform(delete("/api/car/1")
	        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$",equalTo("Car has been deleted successfully")));
	}
	

	
	static String asJsonString(final Object obj) 
	{
	    try 
	    {
	        return new ObjectMapper().writeValueAsString(obj);
	    } 
	    catch (Exception e) 
	    {
	        throw new RuntimeException(e);
	    }
	}

}
