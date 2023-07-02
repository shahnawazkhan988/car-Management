package com.cars.cars.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.cars.cars.entity.CarEntity;
import com.cars.cars.requestmodel.CarRequestModel;

class CarMapperTest 
{
	
	CarMapper carMapper = CarMapper.INSTANCE;
	
	@Test
	void carEntityToCarReqNull()
	{
		assertNull(carMapper.carEntityToCarReqM(null));
	}
	
	@Test
	void carEntityToCarReqEmpty()
	{
		assertNotNull(carMapper.carEntityToCarReqM(new CarEntity()));
	}
	
	
	@Test
	void carEntityToCarReq()
	{
		// given
		CarEntity car = new CarEntity(1L, "toyota", "5000");
		
		//When
		CarRequestModel carReq = carMapper.carEntityToCarReqM(car);
		
		//then
		assertNotNull(carReq);
		assertEquals(1L, carReq.getId());
		assertEquals("toyota", carReq.getName());
		assertEquals("5000", carReq.getPrice());
	}
	
	@Test
	void carReqMTocarEntitysNull()
	{
		assertNull(carMapper.carReqMTocarEntity(null));
	}
	
	@Test
	void carReqMTocarEntitysEmpty()
	{
		assertNotNull(carMapper.carReqMTocarEntity(new CarRequestModel()));
	}
	
	@Test
	void carReqMTocarEntitys()
	{
		// given
		CarRequestModel carreq = new CarRequestModel(1L, "toyota", "5000");
		
		//When
		CarEntity car  = carMapper.carReqMTocarEntity(carreq);
		
		//then
		assertNotNull(car);
		assertEquals(1L, car.getId());
		assertEquals("toyota", car.getName());
		assertEquals("5000", car.getPrice());
	}
	
	
	@Test
	void toCarReqMdNull()
	{
		assertNull(carMapper.toCarReqM(null));
	}
	
	@Test
	void toCarReqMdEmpty()
	{
		List<CarEntity> car = new ArrayList<>(Arrays.asList());
		assertNotNull(carMapper.toCarReqM(car));
	}
	
	@Test
	void toCarReqMd()
	{
		// given
		
		List<CarEntity> car2 = new ArrayList<>(Arrays.asList());
		
		//When
		List<CarRequestModel> carreq = carMapper.toCarReqM(car2);
		
		//then
		assertNotNull(carreq);
		
	}

}
