package com.javaweb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	
	@Autowired
    private BuildingService buildingService; 
    @GetMapping(value="/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam(name="name", required = false) String name,
    									 @RequestParam(name="districtId", required = false) Long districtId,
    									 @RequestParam(name="typeCode", required = false) List<String> typeCode) {
    	List<BuildingDTO> result = buildingService.findAll(name, districtId); 
    	return result; 
    }

//   @PostMapping(value="/api/building/")
//   public Object getBuilding2(@RequestBody BuildingDTO building )
//   {
//	   validate(building); 
//	   return null;
//   }
//   
//   public void validate(BuildingDTO building)
//   {
//	   if (building.getName() == null ||  building.getName() == "" || building.getNumberOfBasement() == null )
//	   {
//		   throw new FieldRequiredException("name or numberofBasement is null");
//	   }
//   }
   @DeleteMapping(value="/api/building/{id}")
   public void deleteBuilding(@PathVariable Integer id)
   {
	   System.out.print("Da xoa thanh cong toa nha" + id + "roi nha"); 
   }
   
}
