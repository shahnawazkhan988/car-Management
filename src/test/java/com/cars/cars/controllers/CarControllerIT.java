package com.cars.cars.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.cars.cars.entity.CarEntity;
import com.cars.cars.repositories.CarRepository;
import com.cars.cars.services.CarService;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerIT 
{
	
	@Autowired
	CarService carService;

	@Autowired
	private CarRepository carRepository;

	@Value("${local.server.port}")
	private int port;

	@BeforeEach
	public void setup() 
	{
		RestAssured.port = port;
		carRepository.deleteAll();
		carRepository.flush();
	}
	
	@Test
	void testGetAllCar() throws Exception 
	{

		// given
		CarEntity car = new CarEntity(1L,"toyota", "5000");

		List<CarEntity> allCars = List.of(car);

		carRepository.saveAll(allCars);
		// when
		when().get("/api/all/car");

		assertEquals(1, carRepository.findAll().size());

	}
	
	@Test
	void testAddNewCar() throws Exception 
	{
		// given
		CarEntity car = new CarEntity(1L,"toyota", "5000");
		
		// when
		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(car).when()
				.post("/api/new/car");

		CarEntity saved = response.getBody().as(CarEntity.class);
		
		assertThat(carRepository.findById(saved.getId())).contains(saved);
		

	}

	
	@Test
	void testUpdateCar() throws Exception 
	{
		CarEntity saved = carRepository
			.save(new CarEntity(null, "toyota", "5000"));
		// given
		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(new CarEntity(null, "toyota2", "10000")).
		// when
		when().
			put("/api/car/" + saved.getId()).
		// then
		then().
			statusCode(200).
			body(
				"id", equalTo(saved.getId().intValue()),
				"name", equalTo("toyota2"),
				"price", equalTo("10000")
			);
	}
	
	
	@Test
	void testDeleteCar() throws Exception {
	// given	
	CarEntity saved = carRepository.save(new CarEntity(null, "toyota", "5000"));
	
	// when
	when().
	delete("/api/car/" + saved.getId());
	
	// then	 
	assertThat(carRepository.findById(saved.getId())).isEmpty();


	}

}
