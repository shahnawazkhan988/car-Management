package com.cars.cars.requestmodel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class CarRequestModel 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Size(min = 3, max = 20, message = "Please write car name in between 20 alphabet")
    private String name;
	private String price;
	
	
	public CarRequestModel() {
		super();
	}
	public CarRequestModel(Long id, String name, String price) 
	{
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	
	

}
