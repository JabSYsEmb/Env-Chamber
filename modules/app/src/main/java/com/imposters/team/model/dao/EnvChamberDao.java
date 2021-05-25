package com.imposters.team.model.dao;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.EnvChamber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnvChamberDao {

    private EnvChamberDao(){
        throw new IllegalStateException("Utility class");
    }

    public static EnvChamber getEnvChamberFromDatabase(int EnvchamberID, MyJDBC db){
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT Envchamber_ID,Ip from Envchamber where Envchamber_ID = ?;")){
            preparedStatement.setInt(1,EnvchamberID);
            ResultSet rs = preparedStatement.executeQuery();
            EnvChamber envChamber = null;
            if(rs.next()) {
                envChamber = new EnvChamber(
                        rs.getInt("Envchamber_ID"),
                        rs.getString("Ip")
                );
            }
            return envChamber;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundEnvChamberInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static List<EnvChamber> getEnvChambersFromDatabase(MyJDBC db){
        List<EnvChamber> envChamber = new ArrayList<>();
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT Envchamber_ID,Ip from Envchamber;")){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                envChamber.add(new EnvChamber(
                        rs.getInt("Envchamber_ID"),
                        rs.getString("Ip"))
                );
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return envChamber;
    }
}

