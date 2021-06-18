package com.imposters.team.dao;

import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.Curve;
import com.imposters.team.model.CurveDefinition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CurveDao
{
    private CurveDao()
    {
        throw new IllegalStateException("Utility class");
    }
    public static Curve getCurveFromDatabase(int Curve_ID, MyJDBC db)
    {
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT Curve_ID,Tasknumber from Curve where Curve_ID = ?;"))
        {
            preparedStatement.setInt(1,Curve_ID);
            ResultSet rs = preparedStatement.executeQuery();
            Curve curve= null;
            if(rs.next())
            {
                curve = new Curve(
                        rs.getInt("Curve_ID"),
                        rs.getString("Tasknumber"),
                        getCurveDefinitionsFromDatabase(Curve_ID,db)
                );
            }
            return curve;
        }
        catch (SQLException | NullPointerException ex)
        {
            System.out.println("NotFoundCurveInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static HashMap<Integer, CurveDefinition> getCurveDefinitionsFromDatabase(int curveID, MyJDBC db)
    {
        HashMap<Integer, CurveDefinition> CurveDefinitions = new HashMap<>();
        int step=1;
        try(PreparedStatement preparedStatement =
                    db.getConnection().prepareStatement("SELECT Duration,Temperature from Curveduration where Curve_ID = ?;"))
        {
            preparedStatement.setInt(1,curveID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                CurveDefinitions.put(step,new CurveDefinition(
                        rs.getInt("Duration"),
                        rs.getInt("Temperature"))
                );
                step++;
            }
        }
        catch (SQLException | NullPointerException ex)
        {
            ex.printStackTrace();
        }
        return CurveDefinitions;
    }
}
