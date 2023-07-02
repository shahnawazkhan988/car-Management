package com.cars.cars;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cars.cars.controllers.CarController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarsApplicationTestIT {

	@Autowired
	CarController carController;
	@Test
	void contextLoads() {
		assertNotNull(carController);
	}
	
}
