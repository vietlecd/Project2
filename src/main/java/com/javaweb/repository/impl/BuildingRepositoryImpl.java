package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuidlingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuidlingRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic"; 
    static final String USER = "root"; 
    static final String PASS = "123456";
    
    public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
        String staffId = (String) params.get("staffId");
        if (StringUtil.checkString(staffId)) {
            sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
        }

        if (typeCode != null && typeCode.size() != 0) {
            sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
            sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
        }

        String rentAreaTo = (String) params.get("areaTo");
        String rentAreaFrom = (String) params.get("areaFrom");

        if (StringUtil.checkString(rentAreaTo) == true || 
            StringUtil.checkString(rentAreaFrom) == true) {
            sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
        }
    }
    
    public static void queryNormal(Map<String, Object> params, StringBuilder where) {
    	for (Map.Entry<String, Object> it : params.entrySet()) {
    		if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && 
    				!it.getKey().startsWith("area") && !it.getKey().startsWith("rentPrice")) {
    			String value = it.getValue().toString(); 
    			if(StringUtil.checkString(value)) {
    				if(NumberUtil.checkNumber(value)) {
    					where.append(" AND b." + it.getKey() + " = " + value );
    				}
    				else {
    					where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' " );
    				}
    			}
    		}
    	}
    }
    
    public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
    	String staffId = (String)params.get ("staffId"); 
    	if (StringUtil.checkString(staffId)) {
    		where.append(" AND assignmentbuilding.staffId = " + staffId);    	
    	}
    	String rentAreaTo = (String) params.get("areaTo");
        String rentAreaFrom = (String) params.get("areaFrom");
        if (StringUtil.checkString(rentAreaTo) == true || StringUtil.checkString(rentAreaFrom) == true)
        {
        	if (StringUtil.checkString(rentAreaFrom) == true) {
        		where.append(" AND rentarea.value >=" + rentAreaFrom); 
        	}
        	if (StringUtil.checkString(rentAreaFrom) == true) {
        		where.append(" AND rentarea.value <=" + rentAreaTo); 
        	}
        }
        
        String rentPriceTo = (String) params.get("rentPriceTo");
        String rentPriceFrom = (String) params.get("rentPriceFrom");
        if (StringUtil.checkString(rentPriceTo) == true || StringUtil.checkString(rentPriceFrom) == true)
        {
        	if (StringUtil.checkString(rentPriceFrom) == true) {
        		where.append(" AND building.rentprice >=" + rentPriceFrom); 
        	}
        	if (StringUtil.checkString(rentAreaFrom) == true) {
        		where.append(" AND building.rentprice <=" + rentPriceTo); 
        	}
        }
        
        if (typeCode != null && typeCode.size() != 0) {
        	where.append(" AND renttype.typecode IN(" + String.join(",", typeCode) + ")");
        }

    }
    @Override
    public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode) {
        StringBuilder finalQuery = new StringBuilder(); 
        StringBuilder whereQuery = new StringBuilder(); 
        finalQuery.append("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.districtid, " + 
                "b.numberofbasement, b.floorarea, b.rentprice, b.managername, " + 
                "b.managerphonenumber, b.servicefee, b.brokeragefee");
        whereQuery.append("WHERE 1 = 1 ");

        List<BuildingEntity> result = new ArrayList<>();
//        if (name != null && !name.equals("")) {
//            sql.append("AND b.name like '%" + name + "%' ");
//        }
//        if (districtId != null) {
//            sql.append("AND b.districtid = " + districtId + " ");
//        }
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(finalQuery.toString());) {
            
            while(rs.next()) {
                BuildingEntity building = new BuildingEntity();
                building.setName(rs.getString("name"));
                building.setStreet(rs.getString("street"));
                building.setWard(rs.getString("ward"));
                building.setNumberOfBasement(rs.getInt("numberofbasement"));
                result.add(building);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result; 
    }
}
