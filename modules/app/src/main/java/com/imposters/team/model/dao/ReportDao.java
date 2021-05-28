package com.imposters.team.model.dao;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.Report;
import com.imposters.team.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    private ReportDao(){
        throw new IllegalStateException("Utility class");
    }

    public static void setReportinDatabase(Report report, MyJDBC db){
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("INSERT INTO Bericht  VALUES (?,?,?,?) ;")){
            preparedStatement.setInt(1,report.getReportID());
            preparedStatement.setInt(2,report.getUser().getId());
            preparedStatement.setInt(3,report.getEnvChamber().getEnvchamberID());
            preparedStatement.setDate(4, null);
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
        }
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("INSERT INTO Test  VALUES (?,?,?,?,?,?,?) ;")){
            int a=0;
            while (report.getTest().size()>a){
                preparedStatement.setInt(1,report.getTest().get(a).getSlot_ID());
                preparedStatement.setInt(2,report.getTest().get(a).getCurve().getId());
                preparedStatement.setInt(3,report.getReportID());
                preparedStatement.setInt(4,report.getTest().get(a).getPrufling().getPruflingID());
                preparedStatement.setBoolean(5,report.getTest().get(a).isFailurestatus());
                preparedStatement.setInt(6,report.getTest().get(a).getTakenduration());
                preparedStatement.setDate(7,null);
                a++;
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundReportInfo, try again!");
            ex.printStackTrace();
        }
    }

}

