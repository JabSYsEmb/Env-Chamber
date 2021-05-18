package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Prufling {
    //Attribute in prufling
  /*public int Prufling_ID;
	public String Serialnumber;
	public int Maxduration;*/
    private int id;
    private SimpleStringProperty serialNumber;
    private SimpleIntegerProperty notExceededDuration;

    public Prufling(int id, int Duration, String serialNumber){
        this.id                     = id;
        this.serialNumber           = new SimpleStringProperty(serialNumber);
        this.notExceededDuration    = new SimpleIntegerProperty(Duration);
    }

    public int getNotExceededDuration() {
        return notExceededDuration.get();
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public int getId() {
        return id;
    }
}
