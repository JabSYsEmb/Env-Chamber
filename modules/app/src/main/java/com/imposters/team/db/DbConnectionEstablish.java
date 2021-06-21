package com.imposters.team.db;

public class DbConnectionEstablish {
    // singleton Design Pattern Applied in to avoid initializing more then one Connection to DB.

    private static volatile DbConnectionEstablish instance;
    protected MyJDBC db;

    private DbConnectionEstablish() {
        this.db = new MyJDBC();
        this.db.createDatabase();
        this.db.insertDataIntoDatabase();
    }

    public static DbConnectionEstablish getInstance() {
        if (instance == null) {
            synchronized (DbConnectionEstablish.class) {
                instance = new DbConnectionEstablish();
            }
        }
        return instance;
    }

    public MyJDBC getDb() {
        return this.db;
    }
}


