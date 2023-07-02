package com.cars.cars.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cars.cars.entity.CarEntity;
import com.cars.cars.requestmodel.CarRequestModel;

@Mapper
public interface CarMapper 
{
	
	CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
	
	CarRequestModel carEntityToCarReqM(CarEntity carEntity);
	CarEntity carReqMTocarEntity(CarRequestModel carReqM);
	List<CarRequestModel> toCarReqM(List<CarEntity> carEntity);
	

}
