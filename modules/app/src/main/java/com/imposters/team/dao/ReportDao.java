package com.imposters.team.dao;

import com.imposters.team.model.Report;
import com.imposters.team.db.MyJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportDao {

    private ReportDao() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean setReportinDatabase(Report report, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("INSERT INTO Bericht (User_ID,Envchamber_ID,Date) VALUES (?,?,?) ;")) {
            db.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
            System.out.println(report);
            preparedStatement.setInt(1, report.getUser().getId());
            preparedStatement.setInt(2, report.getEnvChamber().getEnvchamberID());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss.SSSz");
            LocalDateTime now = LocalDateTime.now();
            preparedStatement.setDate(
                    3, java.sql.Date.valueOf(now.toLocalDate())
            );

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
            return false;
        }
    }
}

