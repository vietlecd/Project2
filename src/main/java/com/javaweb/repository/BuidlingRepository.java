package com.javaweb.repository;

import java.util.ArrayList;
import java.util.List;

import com.javaweb.repository.entity.BuildingEntity;

public interface BuidlingRepository {
	List<BuildingEntity> findAll(String	name, Long districtid);
}
