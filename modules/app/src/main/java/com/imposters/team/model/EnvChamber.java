package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;


public class EnvChamber{
    //Attribute in envchamber
  /*public int Envchamber_ID;
	public String Ip;
	public int temperature;*/
    private int envchamberID;
    private SimpleStringProperty Ip;
    private SimpleIntegerProperty failureRate;
    private SimpleIntegerProperty maxTemperature;

    public EnvChamber(int envchamberID, String ip, int failureRate, int maxTemperature) {
        this.envchamberID = envchamberID;
        this.Ip = new SimpleStringProperty(ip);
        this.failureRate = new SimpleIntegerProperty(failureRate);
        this.maxTemperature = new SimpleIntegerProperty(maxTemperature);
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
