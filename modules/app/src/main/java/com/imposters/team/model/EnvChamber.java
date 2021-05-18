package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.net.Inet4Address;



public class EnvChamber{
    //Attribute in envchamber
  /*public int Envchamber_ID;
	public String Ip;
	public int temperature;*/
    private int id;
    private SimpleStringProperty ip;
    private SimpleIntegerProperty maxTemperature;

    public EnvChamber(int id, String ip, int maxTemperature){
        this.id             = id;
        this.ip             = new SimpleStringProperty(ip);
        this.maxTemperature = new SimpleIntegerProperty(maxTemperature);
    }

    public int getMaxTemperature() {
        return maxTemperature.get();
    }

    public String getIp() {
        return ip.get();
    }

    public int getId() {
        return id;
    }
}
