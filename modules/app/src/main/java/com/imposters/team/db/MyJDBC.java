package com.imposters.team.db;

import java.sql.*;
import java.math.BigInteger;
import java.util.Enumeration;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class MyJDBC {
    //Part 1
    private static final String DB_DEFAULT_DATABASE = "mydb";
    private static final String DB_DEFAULT_SERVER_URL = "172.16.103.136:3310";
    private static final String DB_DEFAULT_ACCOUNT = "sa";
    private static final String DB_DEFAULT_PASSWORD = "123456";

    private final static String DB_DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_DRIVER_PARAMETERS = "";

    private Connection connection = null;


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
            if (!Boolean.TRUE.equals(selectDriver(DB_DRIVER_URL))) {
                return;
            }

            if (password == null) {
                password = "";
            }

            // establish a connection to a named database on a specified server
            String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
            log("Connecting " + connStr);
            this.connection = DriverManager.getConnection(connStr, account, password);
            this.checkDatabaseConnectionValidation(this.connection, 5);
        } catch (SQLException eSQL) {
            error(eSQL);
            this.close();
        }
    }

    // end of constructors

    /* ********************************PART_1************************************** */

    public static String getDBConnectionStatus(MyJDBC db) {
        String status = null;
        try {
            if (db.connection.isValid(5)) {
                status = "The connection to " +
                        getDbDefaultServerUrl() +
                        "/" +
                        getDbDefaultDatabase() +
                        " has been established successfully";
            }
        } catch (SQLException ex) {
            status = "The connection hasn't been established";
            db.error(ex);
        }
        return status;
    }

    public static String getDbDefaultDatabase() {
        return DB_DEFAULT_DATABASE;
    }

    public static String getDbDefaultServerUrl() {
        return DB_DEFAULT_SERVER_URL;
    }

    public static String getDbDefaultAccount() {
        return DB_DEFAULT_ACCOUNT;
    }


    // this part of code needs refactoring.

    public static String getDbDefaultPassword() {
        return DB_DEFAULT_PASSWORD;
    }

    public static String getDbDriverUrl() {
        return DB_DRIVER_URL;
    }



    /* ********************************PART_2************************************** */

    public static String getDbDriverPrefix() {
        return DB_DRIVER_PREFIX;
    }


    /* ********************************PART_3************************************** */

    public static String getDbDriverParameters() {
        return DB_DRIVER_PARAMETERS;
    }

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
            Class.forName(driverName).newInstance();
            // Put all non-preferred drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException ex) {
            error(ex);
            return false;
        }
        return true;
    }

    /* ********************************PART_4************************************** */
    // For error handler and console functions.

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
    public ResultSet executeResultSetQuery(String sql) {
        try (Statement s = this.connection.createStatement()) {
            return s.executeQuery(sql);
        } catch (SQLException ex) {
            error(ex);
            return null;
        }
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
        try (Statement s = this.connection.createStatement()) {
            log(sql);
            int n = s.executeUpdate(sql);
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
        try (Statement s = this.connection.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            log(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException ex) {
            error(ex);
        }
        return result;
    }

    /**
     * *
     * Executes query that is expected to return a list of String values
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringListQuery(String sql) {
        String result = null;
        try (Statement s = this.connection.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            log(sql);
            if (rs.next()) {
                result = rs.getString(5);
            }
        } catch (SQLException ex) {
            error(ex);
        }
        return result;
    }

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
        try (PreparedStatement preparedStatement =
                     this.connection.prepareStatement("UPDATE employee SET " + "password = ? WHERE employeeId = ?")) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userID);

            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

    /**
     * @author Hail Ibrahimoglu
     **/

    // Creating the whole database for the project.
    public void createDatabase() {
        /*
        +---------+---------+------------+----------+----------+-------------+
        | User_ID | Fname   | Lname      | Username | Password | AdminStatus |
        +---------+---------+------------+----------+----------+-------------+
        |       1 | Bianca  | Randermann | Bibo     | 12345    |           1 |
        |       2 | Anna    | Gutenberg  | nino     | 54321    |           0 |
        |       3 | Katrina | Gunther    | kiko     | 14523    |           0 |
        +---------+---------+------------+----------+----------+-------------+
         */

        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + " User_ID INT(10)  NOT NULL AUTO_INCREMENT  PRIMARY KEY ,"
                + " Fname VARCHAR(45),"
                + " Lname VARCHAR(45),"
                + " Username VARCHAR(45),"
                + " Password VARCHAR(129),"
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
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curve ("
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
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curveduration ("
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
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Envchamber ("
                + "  Envchamber_ID INT  NOT NULL AUTO_INCREMENT  PRIMARY KEY ,"
                + "  Ip varchar(45),"
                + "  FailureRate INT,"
                + "  maxTemperature INT,"
                + "  responseTime INT);");

        /*
        +-------------+--------------+-------------+
        | Prufling_ID | Serialnumber | Maxduration |
        +-------------+--------------+-------------+
        |           1 | A10252       |          30 |
        |           2 | A15425       |          20 |
        |           3 | A17777       |          20 |
        +-------------+--------------+-------------+
         */
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Prufling ("
                + "  Prufling_ID  INT  NOT NULL AUTO_INCREMENT PRIMARY KEY ,"
                + "  Slot_ID INT NOT NULL,"
                + "  Serialnumber varchar(45),"
                + "  CurveTaskNumber varchar(45),"
                + "  Status BOOLEAN  );");


        /*
        +------------+---------+---------------+------+
        | Bericht_ID | User_ID | Envchamber_ID | Date |
        +------------+---------+---------------+------+
        |          1 |       2 |             1 | NULL |
        |          2 |       3 |             1 | NULL |
        +------------+---------+---------------+------+
         */
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Bericht ("
                + "  Bericht_ID INT  NOT NULL  AUTO_INCREMENT PRIMARY KEY,"
                + " User_ID INT(10),"
                + "  Envchamber_ID INT,"
                + "  FOREIGN KEY (User_ID) REFERENCES User(User_ID),"
                + "  FOREIGN KEY (Envchamber_ID) REFERENCES Envchamber(Envchamber_ID),"
                + "  Date DATETIME);");


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
        this.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Test ("
                + "  Slot_ID  INT  NOT NULL  ,"
                + "  Curve_ID INT(10),"
                + "  Bericht_ID INT,"
                + "  Prufling_ID  INT,"
                + "  FOREIGN KEY (Curve_ID) REFERENCES Curve(Curve_ID),"
                + "  FOREIGN KEY (Prufling_ID) REFERENCES Prufling(Prufling_ID),"
                + "  FOREIGN KEY (Bericht_ID) REFERENCES Bericht(Bericht_ID),"
                + "  Failurestatus  BOOLEAN,"
                + "  Takenduration  INT,"
                + "  Startingtime DATETIME);");
    }

    /**
     * @author Hail Ibrahimoglu
     **/

    public void insertDataIntoDatabase() {
        final String SET_FOREIGN_KEY_CHECKS_0 = "SET FOREIGN_KEY_CHECKS = 0;";
        final String SET_FOREIGN_KEY_CHECKS_1 = "SET FOREIGN_KEY_CHECKS = 1;";
        this.executeUpdateQuery(SET_FOREIGN_KEY_CHECKS_0);
        //user
        this.executeUpdateQuery("TRUNCATE table User;");
        this.executeUpdateQuery("INSERT INTO User VALUES (1,'Bianca', 'Randermann', 'Bibo', '" + this.passwordEncrypter("12345") + "', true )");
        this.executeUpdateQuery("INSERT INTO User VALUES (2,'Anna', 'Gutenberg', 'nino', '" + this.passwordEncrypter("54321") + "', false )");
        this.executeUpdateQuery("INSERT INTO User VALUES (3,'Katrina', 'Gunther', 'kiko', '" + this.passwordEncrypter("12345") + "', false )");

        //Curve
        this.executeUpdateQuery("TRUNCATE table Curve;");
        this.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (1,'200A')");
        this.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (2,'201A')");
        this.executeUpdateQuery("INSERT INTO Curve (Curve_ID,Tasknumber) VALUES (3,'202A')");

        //Curveduration
        this.executeUpdateQuery("TRUNCATE table Curveduration;");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (1,1,5, 50, 1)");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (2,2,3, 60, 1)");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (3,3,7, -60, 1)");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (4,1,7, 10, 2)");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (5,2,1, 100, 2)");
        this.executeUpdateQuery("INSERT INTO Curveduration VALUES (6,3,9, 3, 2)");

        //Envchamber
        this.executeUpdateQuery("TRUNCATE table Envchamber;");
        this.executeUpdateQuery("INSERT INTO Envchamber VALUES (1,'192.16.103.132',10,90,25)");
        this.executeUpdateQuery("INSERT INTO Envchamber VALUES (2,'192.16.100.2',20,80,20)");
        this.executeUpdateQuery("INSERT INTO Envchamber VALUES (3,'51.16.10.1',20,80,20)");
        this.executeUpdateQuery("INSERT INTO Envchamber VALUES (4,'142.250.187.142',20,80,10)");

        //Prufling
        this.executeUpdateQuery("TRUNCATE table Prufling;");
        this.executeUpdateQuery("INSERT INTO Prufling VALUES (1, 3,'A17777','A203',TRUE)");

        //Bericht
        this.executeUpdateQuery("TRUNCATE table Bericht;");
        this.executeUpdateQuery("INSERT INTO Bericht VALUES (1,2,1,NULL)");
        this.executeUpdateQuery("INSERT INTO Bericht VALUES (2,3,1, NULL)");


        //Test
        this.executeUpdateQuery("TRUNCATE table Test;");
        this.executeUpdateQuery("INSERT INTO Test VALUES (1,1,1,1, true,50,NULL)");
        this.executeUpdateQuery("INSERT INTO Test VALUES (2,2,1,1,true,70,NULL)");
        this.executeUpdateQuery("INSERT INTO Test VALUES (3,2,2,3,false,20, NULL)");
        this.executeUpdateQuery("INSERT INTO Test VALUES (4,1,1,1,false,5, NULL)");
        this.executeUpdateQuery("INSERT INTO Test VALUES (5,2,2,2,true,60, NULL)");
        this.executeUpdateQuery("INSERT INTO Test VALUES (6,3,2,3,false,3, NULL)");

        this.executeUpdateQuery(SET_FOREIGN_KEY_CHECKS_1);
    }

    public void dropDatabase() {
        this.executeUpdateQuery("DROP DATABASE mysql;");
    }

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

        System.err.println(msg);
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
     * @return encrypted password as a String
     */
    public String passwordEncrypter(String passwd) {
        try {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-512");
                // seperates the password into bytes for encyprtion.
                md.update(passwd.getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException ex) {
                System.out.println(ex.getMessage());
            }
            byte[] digest = md.digest();

            return String.format("%064x", new BigInteger(1, digest));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * checks if the connection to a database is still valid or not
     *
     * @param conn    Connection Object for validation checking.
     * @param timeOut The time in seconds to wait for the database operation used to validate the connection to complete.
     */
    public void checkDatabaseConnectionValidation(Connection conn, int timeOut) {
        try {
            if (conn.isValid(timeOut)) {
                log(
                        "The connection to " +
                                DB_DEFAULT_SERVER_URL +
                                "/" +
                                DB_DEFAULT_DATABASE +
                                " has been established successfully."
                );
            }
        } catch (SQLException ex) {
            error(ex);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}


