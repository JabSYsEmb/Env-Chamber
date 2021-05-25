package com.imposters.team.model.dao;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.Prufling;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PruflingDao {

    private PruflingDao(){
        throw new IllegalStateException("Utility class");
    }

    public static Prufling getPruflingFromDatabase(int pruflingID, MyJDBC db){
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT * from Prufling where Prufling_ID = ?;")){
            preparedStatement.setInt(1,pruflingID);
            ResultSet rs = preparedStatement.executeQuery();
            Prufling prufling = null;
            if(rs.next()) {
                prufling = new Prufling(
                        rs.getInt("Prufling_ID"),
                        rs.getString("Serialnumber"),
                        rs.getInt("Maxduration")
                );
            }
            return prufling;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundPruflingInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Prufling> getPruflingsFromDatabase(MyJDBC db){
        List<Prufling> prufling = new ArrayList<>();
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT * from Prufling;")){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                prufling.add(new Prufling(
                        rs.getInt("Prufling_ID"),
                        rs.getString("Serialnumber"),
                        rs.getInt("Maxduration"))
                );
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return prufling;
    }
}

