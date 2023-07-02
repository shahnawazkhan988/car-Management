package com.cars.cars.repositories;
import org.springframework.data.jpa.repository.JpaRepository;	
import org.springframework.stereotype.Repository;

import com.cars.cars.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long>{

}
