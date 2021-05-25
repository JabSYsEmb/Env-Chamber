package com.imposters.team.model.dao;

import com.imposters.team.model.EnvChamber;
import com.imposters.team.db.MyJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnvChamberDao {

    private EnvChamberDao(){
        throw new IllegalStateException("Utility class");
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
                                rs.getString("Ip")
                        ));
            }
            return envChamberList;
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
            return null;
            }
    }
}