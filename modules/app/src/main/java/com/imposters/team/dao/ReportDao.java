package com.imposters.team.dao;

import com.imposters.team.model.Report;
import com.imposters.team.db.MyJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportDao {

    private ReportDao() {
        throw new IllegalStateException("Utility class");
    }

    public static void setReportinDatabase(Report report, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("INSERT INTO Bericht (User_ID,Envchamber_ID,Date) VALUES (?,?,?) ;")) {
            db.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
            preparedStatement.setInt(1, report.getUser().getId());
            preparedStatement.setInt(2, report.getEnvChamber().getEnvchamberID());
            preparedStatement.setDate(3, null);
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
        }
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("INSERT INTO Test  VALUES (?,?,LAST_INSERT_ID(),?,?,?,?) ;")) {
            int a = 0;
            while (report.getTest().size() > a) {
                preparedStatement.setInt(1, report.getTest().get(a).getSlot_ID());
                preparedStatement.setInt(2, report.getTest().get(a).getCurve().getId());
                preparedStatement.setInt(3, report.getTest().get(a).getPrufling().getPruflingID());
                preparedStatement.setBoolean(4, report.getTest().get(a).isFailurestatus());
                preparedStatement.setInt(5, report.getTest().get(a).getTakenduration());
                preparedStatement.setDate(6, null);
                a++;
                preparedStatement.executeUpdate();
            }
            db.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
        }
    }
}

