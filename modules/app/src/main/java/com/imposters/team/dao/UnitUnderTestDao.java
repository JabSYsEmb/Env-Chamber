package com.imposters.team.dao;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.UnitUnderTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitUnderTestDao {

    private UnitUnderTestDao() {
        throw new IllegalStateException("Utility class");
    }

    public static UnitUnderTest getUnitUnderTestFromDBById(int pruflingID, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT * from Prufling where Prufling_ID = ?;")) {
            preparedStatement.setInt(1, pruflingID);
            ResultSet rs = preparedStatement.executeQuery();
            UnitUnderTest unitUnderTest = null;
            if (rs.next()) {
                unitUnderTest = new UnitUnderTest(
                        rs.getInt("Prufling_ID"),
                        rs.getString("Serialnumber")
                );
            }
            return unitUnderTest;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Needed UnitUnderTest NotFound,try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static List<UnitUnderTest> getUnitUnderTestFromDB(MyJDBC db) {
        List<UnitUnderTest> unitUnderTests = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT * from Prufling;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                unitUnderTests.add(new UnitUnderTest(
                        rs.getInt("Prufling_ID"),
                        rs.getString("Serialnumber")
                ));
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return unitUnderTests;
    }
}

