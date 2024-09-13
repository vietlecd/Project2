package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuidlingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	@Autowired
	private BuidlingRepository buildingRepository; 
	
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typecode) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typecode); 
		List<BuildingDTO> result = new ArrayList<BuildingDTO>(); 
		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName()); 
			building.setAddress(item.getStreet() + "," + item.getWard());
			building.setNumberOfBasement(item.getNumberOfBasement()); 
			result.add(building);
		}
		return result; 
	}
}
