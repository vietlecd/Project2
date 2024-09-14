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
