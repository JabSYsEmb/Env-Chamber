package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.net.Inet4Address;
import java.util.Objects;


public class EnvChamber{
    //Attribute in envchamber
  /*public int Envchamber_ID;
	public String Ip;
	public int temperature;*/
    private int EnvchamberID;
    private SimpleStringProperty Ip;

    public EnvChamber(int envchamberID, String ip) {
        this.EnvchamberID = envchamberID;
        this.Ip = new SimpleStringProperty(ip);
    }

    public int getEnvchamberID() {
        return EnvchamberID;
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
                "EnvchamberID=" + EnvchamberID +
                ", Ip=" + Ip +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvChamber that = (EnvChamber) o;
        return EnvchamberID == that.EnvchamberID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(EnvchamberID);
    }
}
