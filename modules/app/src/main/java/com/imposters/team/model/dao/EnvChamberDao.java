package com.imposters.team.model.dao;


import com.imposters.team.model.EnvChamber;
import com.imposters.team.db.MyJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

public class EnvChamberDao {

    private EnvChamberDao(){
        throw new IllegalStateException("Utility class");
    }


    public static EnvChamber getEnvChamberFromDatabase(String EnvchamberIP, MyJDBC db){
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT * from Envchamber where Ip = ?;")){
            preparedStatement.setString(1,EnvchamberIP);
            ResultSet rs = preparedStatement.executeQuery();
            EnvChamber envChamber = null;
            if(rs.next()) {
                envChamber = new EnvChamber(
                        rs.getInt("Envchamber_ID"),
                        rs.getString("Ip"),
                        rs.getInt("FailureRate"),
                        rs.getInt("maxTemperature"),
                        rs.getInt("responseTime")
                );
            }
            return envChamber;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundEnvChamberInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static List<EnvChamber> getEnvChamberFromDatabase(MyJDBC db){
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT * from Envchamber;")){
            ResultSet rs = preparedStatement.executeQuery();
            List<EnvChamber> envChamberList = new ArrayList<>();
            while(rs.next()) {
                envChamberList.add(
                        new EnvChamber(
                                rs.getInt("Envchamber_ID"),
                                rs.getString("Ip"),
                                rs.getInt("FailureRate"),
                                rs.getInt("maxTemperature"),
                                rs.getInt("responseTime")
                        ));
            }
            return envChamberList;
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

