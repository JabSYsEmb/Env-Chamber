package com.imposters.team.db;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Enumeration;

public class MyJDBC {
    //Part 1
    private static final String DB_DEFAULT_DATABASE = "sys";
    private static final String DB_DEFAULT_SERVER_URL = "localhost:3306";
    private static final String DB_DEFAULT_ACCOUNT = "root";
    private static final String DB_DEFAULT_PASSWORD = "root";

    private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_DRIVER_PARAMETERS = "?useSSL=false";

    private Connection connection = null;

    // set for verbose logging of all queries
    private boolean verbose = true;

    // remembers the first error message on the connection
    private String errorMessage = null;

    // constructors
    public MyJDBC() {
        this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName) {
        this(dbName, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName, String account, String password) {
        this(dbName, DB_DEFAULT_SERVER_URL, account, password);
    }

    public MyJDBC(String dbName, String serverURL, String account, String password) {
        try {
            // verify that a proper JDBC driver has been installed and linked
            if (!selectDriver(DB_DRIVER_URL)) {
                return;
            }

            if (password == null) {
                password = "";
            }

            // establish a connection to a named database on a specified server
            String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
            log("Connecting " + connStr);
            this.connection = DriverManager.getConnection(connStr, account, password);

        } catch (SQLException eSQL) {
            error(eSQL);
            this.close();
        }
    }
    // end of constructors

/* ********************************PART_1************************************** */

    /**
     * *
     * elects proper loading of the named driver for database connections. This
     * is relevant if there are multiple drivers installed that match the JDBC
     * type. Instead of Class.forName(JDBM).newInstance() which isn't compatible with
     * last versions of Java Runtime Environment (jre)
     *
     * @param driverName the name of the driver to be activated.
     * @return indicates whether a suitable driver is available
     */
    private Boolean selectDriver(String driverName) {
        try {
            try {
                Class.forName(driverName).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // Put all non-preferred drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            error(ex);
            return false;
        }
        return true;
    }

    /**
     * *
     * Executes an SQL query that yields a ResultSet with the outcome of the
     * query. This outcome may be a single row with a single column in case of a
     * scalar outcome.
     *
     * @param sql the full sql text of the query.
     * @return a ResultSet object that can iterate along all rows
     * @throws SQLException
     */
    public ResultSet executeResultSetQuery(String sql) throws SQLException {
        Statement s = this.connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        return rs;
    }

    /**
     * *
     * Executes a DDL (Data Definition Language), DML (Data Manipulation Language)
     * or DCL (Data Control Language) query that does not yield a ResultSet.
     *
     * @param sql the full sql text of the query.
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeUpdateQuery(String sql) {
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            int n = s.executeUpdate(sql);
            s.close();
            return (n);
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

    /**
     * *
     * Executes query that is expected to return a single String value
     * For instance, returning first column of a specific table in db.
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and Resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }

    /*{*/
    // this part of code needs refactoring.

    /**
     * *
     * Executes query that is expected to return a list of String values
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringListQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }/*}*/

    /**
     * *
     * Sets a new password for a user according to his/her ID
     *
     * @param userID      the id of an user/admin.
     * @param newPassword the new password.
     *                    [see link below to encrypt the password!]
     *                    (https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java/2861125#2861125)
     * @return (1) the row count for SQL Data Manipulation Language (DML) statements or
     * (2) 0 for SQL statements that return nothing
     * (3) -1 in case function call throws a SQLException
     */
    public int executeUserPasswordUpdateQuery(String userID, String newPassword) throws SQLException {

        try {

            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE employee SET "
                    + "password = ? WHERE employeeId = ?");

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userID);

            int returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println(returnValue);

            return returnValue;
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

/* ********************************PART_2************************************** */
     /**
     * @author Hail Ibrahimoglu
     * 
     **/

    // Creating the whole database for the project.
    public static void createEnvChamberDatabase(String dbName) {

        System.out.println("Creating the " + dbName + " database...");

        MyJDBC sysJDBC = new MyJDBC("mydb");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
        sysJDBC.close();

        System.out.println("Creating the User table...");
        MyJDBC myJDBC = new MyJDBC(dbName);

        /*
        +---------+---------+------------+----------+----------+-------------+
        | User_ID | Fname   | Lname      | Username | Password | AdminStatus |
        +---------+---------+------------+----------+----------+-------------+
        |       1 | Bianca  | Randermann | Bibo     | 12345    |           1 |
        |       2 | Anna    | Gutenberg  | nino     | 54321    |           0 |
        |       3 | Katrina | Gunther    | kiko     | 14523    |           0 |
        +---------+---------+------------+----------+----------+-------------+
         */

        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + " User_ID INT(10)  NOT NULL AUTO_INCREMENT  PRIMARY KEY ,"
                + " Fname VARCHAR(45),"
                + " Lname VARCHAR(45),"
                + " Username VARCHAR(45),"
                + " Password VARCHAR(10),"
                + " AdminStatus  BOOLEAN);");

        /*
        +----------+------------+----------+
        | Curve_ID | Tasknumber | Duration |
        +----------+------------+----------+
        |        1 | 200A       | NULL     |
        |        2 | 201A       | NULL     |
        |        3 | 202A       | NULL     |
        +----------+------------+----------+
             */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curve ("
                + "Curve_ID INT(10) NOT NULL AUTO_INCREMENT  PRIMARY KEY,"
                + " Tasknumber VARCHAR(45),"
                + " Duration VARCHAR(100));");

        /*
        +------+------+----------+-------------+----------+
        | CDID | Step | Duration | Temperature | Curve_ID |
        +------+------+----------+-------------+----------+
        |    1 |    1 |        5 |          50 |        1 |
        |    2 |    2 |        3 |          60 |        1 |
        |    3 |    3 |        7 |         -60 |        1 |
        |    4 |    1 |        7 |          10 |        2 |
        |    5 |    2 |        1 |         100 |        2 |
        |    6 |    3 |        9 |           3 |        2 |
        +------+------+----------+-------------+----------+
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curveduration ("
                + "CDID INT(10) NOT NULL   PRIMARY KEY,"
                + "Step INT(10) NOT NULL ,"
                + " Duration INT,"
                + " Temperature INT,"
                + " Curve_ID INT,"
                + " FOREIGN KEY (Curve_ID) REFERENCES Curve(Curve_ID));");

        /*
        +---------------+-----------+-------------+
        | Envchamber_ID | Ip        | temperature |
        +---------------+-----------+-------------+
        |             1 | 127.04.39 |        NULL |
        |             2 | 536.02.01 |        NULL |
        +---------------+-----------+-------------+
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Envchamber ("
                + "  Envchamber_ID INT  NOT NULL AUTO_INCREMENT  PRIMARY KEY ,"
                + "  Ip varchar(45),"
                + "  temperature INT);");

        /*
        +-------------+--------------+-------------+
        | Prufling_ID | Serialnumber | Maxduration |
        +-------------+--------------+-------------+
        |           1 | A10252       |          30 |
        |           2 | A15425       |          20 |
        |           3 | A17777       |          20 |
        +-------------+--------------+-------------+
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Prufling ("
                + "  Prufling_ID  INT  NOT NULL  PRIMARY KEY ,"
                + "  Serialnumber varchar(45),"
                + "  Maxduration INT);");


        /*
        +------------+---------+---------------+------+
        | Bericht_ID | User_ID | Envchamber_ID | Date |
        +------------+---------+---------------+------+
        |          1 |       2 |             1 | NULL |
        |          2 |       3 |             1 | NULL |
        +------------+---------+---------------+------+
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Bericht ("
                + "  Bericht_ID INT  NOT NULL  PRIMARY KEY,"
                + " User_ID INT(10),"
                + "  Envchamber_ID INT,"
                + "  FOREIGN KEY (User_ID) REFERENCES User(User_ID),"
                + "  FOREIGN KEY (Envchamber_ID) REFERENCES Envchamber(Envchamber_ID),"
                + "  Date DATETIME(6) );");


        /*
        +---------+----------+------------+-------------+---------------+---------------+--------------+
        | Slot_ID | Curve_ID | Bericht_ID | Prufling_ID | Failurestatus | Takenduration | Startingtime |
        +---------+----------+------------+-------------+---------------+---------------+--------------+
        |       1 |        1 |          1 |           1 |             1 |            50 | NULL         |
        |       2 |        2 |          1 |           1 |             1 |            70 | NULL         |
        |       3 |        2 |          2 |           3 |             0 |            20 | NULL         |
        |       4 |        1 |          1 |           1 |             0 |             5 | NULL         |
        |       5 |        2 |          2 |           2 |             1 |            60 | NULL         |
        |       6 |        3 |          2 |           3 |             0 |             3 | NULL         |
        +---------+----------+------------+-------------+---------------+---------------+--------------+
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Test ("
                + "  Slot_ID  INT  NOT NULL  PRIMARY KEY ,"
                + "  Curve_ID INT(10),"
                + "  Bericht_ID INT,"
                + "  Prufling_ID  INT,"
                + "  FOREIGN KEY (Curve_ID) REFERENCES Curve(Curve_ID),"
                + "  FOREIGN KEY (Prufling_ID) REFERENCES Prufling(Prufling_ID),"
                + "  FOREIGN KEY (Bericht_ID) REFERENCES Bericht(Bericht_ID),"
                + "  Failurestatus  BOOLEAN,"
                + "  Takenduration  INT,"
                + "  Startingtime DATETIME(6) );");

        myJDBC.close();
    }


    /* ********************************PART_3************************************** */
    
    /**
    * @author Hail Ibrahimoglu
    * 
    **/
    
    public static void insertDataIntoDatabase(String dbName){
        
        //Get connected to database
        MyJDBC myJDBC = new MyJDBC(dbName);

        //user
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table User;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES (1,'Bianca', 'Randermann', 'Bibo', '12345', true )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES (2,'Anna', 'Gutenberg', 'nino', '54321', false )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES (3,'Katrina', 'Gunther', 'kiko', '14523', false )");

        //Curve
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Curve;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (1,'200A')");
        myJDBC.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (2,'201A')");
        myJDBC.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (3,'202A')");

        //Curveduration
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Curveduration;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (1,1,5, 50, 1)");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (2,2,3, 60, 1)");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (3,3,7, -60, 1)");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (4,1,7, 10, 2)");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (5,2,1, 100, 2)");
        myJDBC.executeUpdateQuery("INSERT INTO Curveduration VALUES (6,3,9, 3, 2)");

        //Envchamber
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Envchamber;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Envchamber VALUES (1,'127.04.39',NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Envchamber VALUES (2,'536.02.01',NULL)");

        //Prufling
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Prufling;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Prufling VALUES (1,'A10252',30)");
        myJDBC.executeUpdateQuery("INSERT INTO Prufling VALUES (2,'A15425',20)");
        myJDBC.executeUpdateQuery("INSERT INTO Prufling VALUES (3,'A17777',20)");

        //Bericht
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Bericht;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Bericht VALUES (1,2,1,NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Bericht VALUES (2,3,1, NULL)");


        //Test
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 0;");
        myJDBC.executeUpdateQuery("TRUNCATE table Test;");
        myJDBC.executeUpdateQuery("SET FOREIGN_KEY_CHECKS = 1;");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (1,1,1,1, true,50,NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (2,2,1,1,true,70,NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (3,2,2,3,false,20, NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (4,1,1,1,false,5, NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (5,2,2,2,true,60, NULL)");
        myJDBC.executeUpdateQuery("INSERT INTO Test VALUES (6,3,2,3,false,3, NULL)");
        myJDBC.close();
    }

/* ********************************PART_4************************************** */
    // For error handler and console functions.

    /**
     * *
     * echoes an exception and its stack trace on the system console. remembers
     * the message of the first error that occurs for later reference. closes
     * the connection such that no further operations are possible.
     *
     * @param e
     */
    public final void error(Exception e) {
        String msg = "MyJDBC-" + e.getClass().getName() + ": " + e.getMessage();

        // capture the message of the first error of the connection
        if (this.errorMessage == null) {
            this.errorMessage = msg;
        }
        System.out.println(msg);
        e.printStackTrace();

        // if an error occurred, close the connection to prevent further operations
        this.close();
    }

    public final void close() {

        if (this.connection == null) {
            // db has been closed earlier already
            return;
        }
        try {
            this.connection.close();
            this.connection = null;
            this.log("Data base has been closed");
        } catch (SQLException eSQL) {
            error(eSQL);
        }
    }

    /**
     * *
     * echoes a message on the system console, if run in verbose mode
     *
     * @param message value is true by default for inspecting
     */
    public void log(String message) {
        if (isVerbose()) {
            System.out.println("MyJDBC: " + message);
        }
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * encrypts input password by an user via SHA-512 encryption
     * digest algorithm into 128 characters before storing it in 
     * Database.
     * 
     * @param passwd the unencrypted password that is passed by an user
     *               to the database.
     * @return       encrypted password as a String
     */
    public String Encrypter(String passwd){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-512");
        }catch(NoSuchAlgorithmException ex){
            System.out.println(ex.getMessage());
        }
        // seperates the password into bytes for encyprtion.
        md.update(passwd.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        
        String encryptedPasswd = String.format("%064x",new BigInteger(1,digest));
        return encryptedPasswd;
    }
}
