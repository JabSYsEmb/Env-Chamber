package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Date;

public class Report {
    private int                     id;
    private Date                    date;
    private SimpleIntegerProperty   userID;
    private SimpleStringProperty    userLastname;
    private SimpleStringProperty    envChamberIP;
    private SimpleIntegerProperty   takenDuration;
    private SimpleStringProperty    userFirstname;
    private SimpleStringProperty    pruflingSerialNumber;
    private SimpleStringProperty    appliedCurveTasknumber;

    public Report(int id, User observer, EnvChamber envChamber, Curve curve, Prufling prufling){
        this.id                     = id;
        this.date                   = new Date();
        this.userID                 = new SimpleIntegerProperty(Integer.parseInt(observer.getId()));
        this.userLastname           = new SimpleStringProperty(observer.getLastName());
        this.envChamberIP           = new SimpleStringProperty(envChamber.getIp());
        this.userFirstname          = new SimpleStringProperty(observer.getFirstName());
        this.takenDuration          = new SimpleIntegerProperty(curve.getWholeDurationInSeconds());
        this.pruflingSerialNumber   = new SimpleStringProperty(prufling.getSerialNumber());
        this.appliedCurveTasknumber = new SimpleStringProperty(curve.getTaskNumber());
    }

    public Date getDate() {
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
