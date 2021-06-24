package com.imposters.team.dao;

import com.imposters.team.model.CurveDefinition;
import com.imposters.team.model.Curve;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.EnvChamber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurveDao {
    private CurveDao() {
        throw new IllegalStateException("Utility class");
    }

    public static Curve getCurveFromDatabase(int Curve_ID, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT Curve_ID,Tasknumber from Curve where Curve_ID = ?;")) {
            preparedStatement.setInt(1, Curve_ID);
            ResultSet rs = preparedStatement.executeQuery();
            Curve curve = null;
            if (rs.next()) {
                curve = new Curve(
                        rs.getInt("Curve_ID"),
                        rs.getString("Tasknumber"),
                        getCurveDefinitionsFromDatabase(Curve_ID, db)
                );
            }
            return curve;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundCurveInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static Curve getCurveFromDatabaseByTaskNumber(String taskNumber, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT Curve_ID,Tasknumber from Curve where taskNumber = ?;")) {
            preparedStatement.setString(1, taskNumber);
            ResultSet rs = preparedStatement.executeQuery();
            Curve curve = null;
            if (rs.next()) {
                curve = new Curve(
                        rs.getInt("Curve_ID"),
                        rs.getString("Tasknumber"),
                        getCurveDefinitionsFromDatabase(rs.getInt("Curve_ID"), db)
                );
            }
            return curve;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundCurveInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static HashMap<Integer, CurveDefinition> getCurveDefinitionsFromDatabase(int curveID, MyJDBC db) {
        HashMap<Integer, CurveDefinition> CurveDefinitions = new HashMap<>();
        int step = 1;
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT Duration,Temperature from Curveduration where Curve_ID = ?;")) {
            preparedStatement.setInt(1, curveID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CurveDefinitions.put(step, new CurveDefinition(
                        rs.getInt("Temperature"),
                        rs.getInt("Duration"))
                );
                step++;
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return CurveDefinitions;
    }

    public static List<Curve> getCurvesFromDatabase(MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT * from Curve;")) {
            ResultSet rs = preparedStatement.executeQuery();
            List<Curve> curveArrayList = new ArrayList<>();
            while (rs.next()) {
                curveArrayList.add(
                        new Curve(
                                rs.getInt("Curve_ID"),
                                rs.getString("Tasknumber")
                        ));
            }
            return curveArrayList;
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
