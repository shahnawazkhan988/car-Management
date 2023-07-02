package com.cars.cars.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.cars.cars.entity.CarEntity;
import com.cars.cars.mapper.CarMapper;
import com.cars.cars.repositories.CarRepository;
import com.cars.cars.requestmodel.CarRequestModel;

@Service
public class CarService 
{
	private CarRepository carRepository;
	
	private static final CarMapper carMapper = CarMapper.INSTANCE;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	public List<CarRequestModel> getAllCar() {
		var carentities = carRepository.findAll();
		return carMapper.toCarReqM(carentities);
	}
	
	public CarRequestModel insertNewCar(CarRequestModel car) 
	{
		CarEntity carEntity = carMapper.carReqMTocarEntity(car);
		
		CarEntity savedEntity = carRepository.save(carEntity);
		return  carMapper.carEntityToCarReqM(savedEntity);
	}

	public CarRequestModel updateCar(Long id, CarRequestModel car) {
		
		CarEntity carEntity = carMapper.carReqMTocarEntity(car);
		carEntity.setId(id);
		
		var savedEntity = carRepository.save(carEntity);

        return carMapper.carEntityToCarReqM(savedEntity);
	}
	
	public void deleteCarById(Long id) 
	{
		carRepository.deleteById(id);
	}
	

}
