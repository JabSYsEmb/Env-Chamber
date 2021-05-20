package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;


public class Report {
    //Attribute in Bericht
    /*public int Bericht_ID;
    public int User_ID;
    public int Envchamber_ID;
    public java.sql.Date Date*/
    // Wir brauchen hier eine Array for testobjekte

    private int id;
    private final SimpleStringProperty    date;
    private final SimpleIntegerProperty   userID;
    private final SimpleStringProperty    userLastname;
    private final SimpleStringProperty    envChamberIP;
    private final SimpleIntegerProperty   takenDuration;
    private final SimpleStringProperty    userFirstname;
    private final SimpleStringProperty    pruflingSerialNumber;
    private final SimpleStringProperty    appliedCurveTasknumber;

    public Report(User observer, EnvChamber envChamber, Curve curve, Prufling prufling){
        this.id                     = this.hashCode();
        this.date                   = new SimpleStringProperty(new Date().toString());
        this.userID                 = new SimpleIntegerProperty(observer.getId());
        this.userLastname           = new SimpleStringProperty(observer.getLastName());
        this.envChamberIP           = new SimpleStringProperty(envChamber.getIp());
        this.userFirstname          = new SimpleStringProperty(observer.getFirstName());
        this.takenDuration          = new SimpleIntegerProperty();
        this.pruflingSerialNumber   = new SimpleStringProperty(prufling.getSerialNumber());
        this.appliedCurveTasknumber = new SimpleStringProperty(curve.getTaskNumber());
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public int getUserID() {
        return userID.get();
    }

    public SimpleIntegerProperty userIDProperty() {
        return userID;
    }

    public String getUserLastname() {
        return userLastname.get();
    }

    public SimpleStringProperty userLastnameProperty() {
        return userLastname;
    }

    public String getEnvChamberIP() {
        return envChamberIP.get();
    }

    public SimpleStringProperty envChamberIPProperty() {
        return envChamberIP;
    }

    public int getTakenDuration() {
        return takenDuration.get();
    }

    public SimpleIntegerProperty takenDurationProperty() {
        return takenDuration;
    }

    public String getUserFirstname() {
        return userFirstname.get();
    }

    public SimpleStringProperty userFirstnameProperty() {
        return userFirstname;
    }

    public String getPruflingSerialNumber() {
        return pruflingSerialNumber.get();
    }

    public SimpleStringProperty pruflingSerialNumberProperty() {
        return pruflingSerialNumber;
    }

    public String getAppliedCurveTasknumber() {
        return appliedCurveTasknumber.get();
    }

    public SimpleStringProperty appliedCurveTasknumberProperty() {
        return appliedCurveTasknumber;
    }

}