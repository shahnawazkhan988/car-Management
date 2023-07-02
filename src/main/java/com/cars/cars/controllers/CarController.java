package com.cars.cars.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cars.cars.requestmodel.CarRequestModel;
import com.cars.cars.services.CarService;

@RestController
@RequestMapping("/api")
public class CarController 
{	
	private final CarService carServiceR;
	
	public CarController(CarService ptservice) {
			
			this.carServiceR = ptservice;
		}

	
	@GetMapping("/all/car")
	public List<CarRequestModel> allcar() 
	{
		return carServiceR.getAllCar();
	}
	
	@PostMapping(value ="/new/car", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addNewCar(@Valid @RequestBody CarRequestModel car, BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) {
			Map<String, String> errorsMap = new HashMap<>();
			bindingResult.getFieldErrors()
					.forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
			return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(carServiceR.insertNewCar(car), HttpStatus.CREATED);
	}
	
	@PutMapping("/car/{id}")
	public ResponseEntity<Object> updateCarInfo(@PathVariable Long id, @RequestBody CarRequestModel car) 
	{
		return new ResponseEntity<>(carServiceR.updateCar(id, car), HttpStatus.OK);
	}
	
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Object>  deleteCar(@PathVariable Long id) {

		carServiceR.deleteCarById(id);
        return new ResponseEntity<>("Car has been deleted successfully",HttpStatus.OK);			

	}
}
