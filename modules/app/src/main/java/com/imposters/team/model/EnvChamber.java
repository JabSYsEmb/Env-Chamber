package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;


public class EnvChamber{

    private int envchamberID;
    private SimpleStringProperty Ip;
    private SimpleIntegerProperty failureRate;
    private SimpleIntegerProperty maxTemperature;
    private SimpleIntegerProperty acceptedResponseTime;

    public EnvChamber(int envchamberID, String ip, int failureRate, int maxTemperature, int acceptedResponseTime) {
        this.envchamberID = envchamberID;
        this.Ip = new SimpleStringProperty(ip);
        this.failureRate = new SimpleIntegerProperty(failureRate);
        this.maxTemperature = new SimpleIntegerProperty(maxTemperature);
        this.acceptedResponseTime = new SimpleIntegerProperty(acceptedResponseTime);
    }

    public EnvChamber(int envchamberID, String ip) {
        this.envchamberID = envchamberID;
        this.Ip = new SimpleStringProperty(ip);
    }

    public int getEnvchamberID() {
        return envchamberID;
    }

    public String getIp() {
        return Ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return Ip;
    }

    @Override
    public String toString() {
        return "EnvChamber{" +
                "EnvchamberID=" + envchamberID +
                ", Ip=" + Ip +
                '}';
    }

    public int getFailureRate(){
        return this.failureRate.get();
    }
    public int getAcceptedResponseTime(){
        return this.acceptedResponseTime.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvChamber that = (EnvChamber) o;
        return envchamberID == that.envchamberID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(envchamberID);
    }
}
