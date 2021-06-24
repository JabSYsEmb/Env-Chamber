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
                        rs.getInt("Slot_ID"),
                        rs.getString("Serialnumber"),
                        rs.getString("CurveTaskNumber"),
                        rs.getBoolean("Status")
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
                        rs.getInt("Slot_ID"),
                        rs.getString("Serialnumber"),
                        rs.getString("CurveTaskNumber"),
                        rs.getBoolean("Status")
                ));
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return unitUnderTests;
    }

    public static void insertUnitUnderTestIntoDB(UnitUnderTest unitUnderTest,MyJDBC db){
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("INSERT INTO Prufling (Slot_ID,Serialnumber,CurveTaskNumber, Status) VALUES (?,?,?,?) ;")) {
            db.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
            preparedStatement.setInt(1, unitUnderTest.getSlotId());
            preparedStatement.setString(2, unitUnderTest.getSerialNumber());
            preparedStatement.setString(3, unitUnderTest.getCurveTaskNumber());
            preparedStatement.setBoolean(3, unitUnderTest.isStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
        }
    }
}

