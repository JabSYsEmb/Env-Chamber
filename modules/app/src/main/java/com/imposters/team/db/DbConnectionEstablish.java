package com.imposters.team.db;

public class DbConnectionEstablish {
    // singleton Design Pattern Applied in to avoid initializing more then one Connection to DB.

    protected MyJDBC db;

    private static volatile DbConnectionEstablish instance;

    public static DbConnectionEstablish getInstance(){
        if(instance == null) {
            synchronized (DbConnectionEstablish.class){
                instance = new DbConnectionEstablish();
            }
        }
        return instance;
    }

    private DbConnectionEstablish(){
        this.db = new MyJDBC();
    }

    public MyJDBC getDb() {
        return this.db;
    }
}


