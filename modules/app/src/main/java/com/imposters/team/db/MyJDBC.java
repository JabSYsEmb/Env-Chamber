package com.imposters.team.db;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Enumeration;

public class MyJDBC {
    private static final String DB_DEFAULT_DATABASE = "testdb";
    private static final String DB_DEFAULT_SERVER_URL = "file";
    private static final String DB_DEFAULT_ACCOUNT = "SA";
    private static final String DB_DEFAULT_PASSWORD = "";
    // mysql  database : use "com.mysql.cj.jdbc.Driver"
    // hsqldb database : use "org.hsqldb.jdbcDriver" OR org.hsqldb.jdbc.JDBCDriver
    private final static String DB_DRIVER_URL = "org.hsqldb.jdbc.JDBCDriver";
    private final static String DB_DRIVER_PREFIX = "jdbc:hsqldb:";

    private Connection connection = null;
    private boolean verbose = true; // set for verbose logging of all queries
    private String errorMessage = null; // remembers the first error message on the connection

    // Starts of constructors
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
            String connStr = DB_DRIVER_PREFIX + serverURL + ":" + dbName;
            log("Connecting " + connStr + "...");
            this.connection = DriverManager.getConnection(connStr, account, password);
            if (!this.connection.isClosed()) {
                System.out.println(
                        "The connection has been established successfully to " +
                                dbName
                );
            }
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

    // Creating the whole database for the project.
    public static void createEnvChamberDatabase(String ccm) {

        System.out.println("Creating the " + ccm + " database...");

        MyJDBC sysJDBC = new MyJDBC("sys");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + ccm);
        sysJDBC.close();

        System.out.println("Creating the User table...");
        MyJDBC myJDBC = new MyJDBC(DB_DEFAULT_DATABASE);

        /*
         ***************************************************************
         * UserId * Fname * Lname * Username * Password * AdminsStatus *
         * *************************************************************
         *  Int   *VARCHAR*VARCHAR* VARCHAR  * VARCHAR  *  Boolean Bit *
         *   0    * Ahmed * Must  *  Adminz  *SH63NX6385*     True     *
         *        *       *       *          *287K....  *              *
         *   1    *  ...  *  ...  *   ...    *   ...    *      ...     *
         *        *       *       *          *          *              *
         * *************************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + " User_ID INT(10)  INT  NOT NULL IDENTITY PRIMARY KEY,"
                + " Fname VARCHAR(45),"
                + " Lname VARCHAR(45),"
                + " Username VARCHAR(45),"
                + " Password VARCHAR(10),"
                + " AdminStatus BIT(1) ");

        /*
             ***************************************************
             * Curve_ID * Tasknumber * Duration * Temperature  *
             * *************************************************
             *  Int     *VARCHAR     *VARCHAR   *  Boolean Bit *
             *   0      * Ahmed      * Must     *     True     *
             *          *            *          *              *
             *   1      *  ...       *  ...     *      ...     *
             *          *            *          *              *
             *          *            *          *              *
             *   9..    *            *          *      ...     *
             * *************************************************
             */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curve ("
                + "Curve_ID INT(10)  INT  NOT NULL IDENTITY PRIMARY KEY,"
                + " Tasknumber VARCHAR(45),"
                + " Duration VARCHAR(100),"
                + " Temperature VARCHAR(100),");

        /*
         ***************************************************
         * CDID_INT *Curve_ID FK * Duration * Temperature  *
         * *************************************************
         *  Int     *VARCHAR     *VARCHAR   *  Boolean Bit *
         *   0      * Ahmed      * Must     *     True     *
         *          *            *          *              *
         *   1      *  ...       *  ...     *      ...     *
         *          *            *          *              *
         *          *            *          *              *
         *   9..    *            *          *      ...     *
         * *************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Curveduration ("
                + "CDID_INT(10)  INT  NOT NULL IDENTITY PRIMARY KEY,"
                + " Duration INT(),"
                + " Temperature INT(),"
                + " FOREIGN KEY (Curve_ID) REFERENCES Curve(Curve_ID),");

        /*
         ***************************************************
         * EnvchamberID  *     Ip     *  Maxtemperature    *
         * *************************************************
         *  Int          * 127.0.0.1  *                    *
         *   0           *            *                    *
         *               *            *                    *
         *   1           *  ...       *                    *
         *               *            *                    *
         *               *            *                    *
         *   9..         *            *                    *
         * *************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Envchamber ("
                + "  Envchamber_ID (10)  INT  NOT NULL IDENTITY PRIMARY KEY ,"
                + "  Ip varchar(45),"
                + "  Maxtemperature INT");

        /*
         ***************************************************************
         *            *           *            *          *            *
         * *************************************************************
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         * *************************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Prufling ("
                + "  Prufling_ID (10)  INT  NOT NULL IDENTITY PRIMARY KEY ,"
                + "  Serialnumber INT,"
                + "  Maxduration INT");

        /*
         ***************************************************************
         *            *           *            *          *            *
         * *************************************************************
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         * *************************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Test ("
                + "  Slot_ID (10)  INT  NOT NULL IDENTITY PRIMARY KEY ,"
                + "  FOREIGN KEY (Curve_ID) REFERENCES Curve(Curve_ID),"
                + "  FOREIGN KEY (Prufling_ID) REFERENCES Purfling(Prufling_ID),"
                + "  Failurestatus BIT(1),"
                + "  Takenduration  INT "
                + "  Startingtime TIME ");

        /*
         ***************************************************************
         *            *           *            *          *            *
         * *************************************************************
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         *            *           *            *          *            *
         * *************************************************************
         */
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Bericht ("
                + "  Bericht_ID INT  NOT NULL IDENTITY PRIMARY KEY,"
                + "  FOREIGN KEY (Test_ID) REFERENCES Test(Test_ID),"
                + "  FOREIGN KEY (User_ID) REFERENCES User(User_ID),"
                + "  FOREIGN KEY (Envchamber_ID) REFERENCES"
                + "  FOREIGN KEY (Envchamber_ID)Envchamber(Envchamber_ID),"
                + "  Date DATE ,");
}

/* ********************************PART_3************************************** */
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
