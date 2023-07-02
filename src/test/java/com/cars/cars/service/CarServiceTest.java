package com.cars.cars.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.cars.cars.entity.CarEntity;
import com.cars.cars.repositories.CarRepository;
import com.cars.cars.requestmodel.CarRequestModel;
import com.cars.cars.services.CarService;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarServiceTest 
{
	
	@Mock
	private CarRepository carRepository;
	
	@InjectMocks
	private CarService carService;
	
	@Test
	void testGetAllCar() 
	{
		//given
		CarEntity car = new CarEntity(1L, "toyota", "5000");
		
		CarRequestModel carreq = new CarRequestModel(1L, "toyota", "5000");
		// When
		when(carRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(car)));
		// then
		assertThat(carreq.getId()).isNotNull();		
		assertThat(carService.getAllCar()).isNotNull();
	}
	
	
	@Test
	void testInsertNewCar() throws Exception {
		//given
		CarEntity car = new CarEntity(null, "toyota", "5000");
		CarRequestModel carreq = new CarRequestModel(1L, "toyota", "5000");
		when(carRepository.save(any(CarEntity.class))).thenReturn(new CarEntity(1L, "toyota", "5000"));
		// When
		when(carRepository.save(any(CarEntity.class)))
			.thenReturn(car);

		CarRequestModel result = carService.insertNewCar(carreq);
		// then
		assertThat(result).isNotNull();
	}

	
	@Test
	void testUpdate() {
		// given
		CarRequestModel carreq = new CarRequestModel(1L, "toyota", "5000");
		
		given(carRepository.findById(anyLong())).willReturn(Optional.of(new CarEntity(1L, "toyota", "5000")));
		given(carRepository.save(any(CarEntity.class))).willReturn(new CarEntity(1L, "toyota", "5000"));

		// When
		CarRequestModel car1 = carService.updateCar(1L, carreq);
		// Then		
		assertNotNull(car1.getId());

		then(carRepository).should().save(any(CarEntity.class));
		then(carRepository).shouldHaveNoMoreInteractions();
		then(carRepository).shouldHaveNoMoreInteractions();

	}
	
	@Test
	void testDelete() throws Exception {
		// given // When
		carService.deleteCarById(1L);

		// then
		then(carRepository).should().deleteById(anyLong());
		then(carRepository).shouldHaveNoMoreInteractions();
	}
}
