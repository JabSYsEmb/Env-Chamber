package com.imposters.team.model.dao;

import com.imposters.team.App;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.Curve;

public class CurveDao {

    private MyJDBC db = App.getDatabase();

    Curve curve;
}
