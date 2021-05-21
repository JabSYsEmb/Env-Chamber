package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Prufling {
    //Attribute in prufling
  /*public int Prufling_ID;
	public String Serialnumber;
	public int Maxduration;*/
    private int PruflingID;
    private SimpleStringProperty serialNumber;
    private SimpleIntegerProperty Maxduration;

    public Prufling(int pruflingID, String serialNumber, int maxduration) {
        this.PruflingID = pruflingID;
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.Maxduration = new SimpleIntegerProperty(maxduration);
    }

    public int getPruflingID() {
        return PruflingID;
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public SimpleStringProperty serialNumberProperty() {
        return serialNumber;
    }

    public int getMaxduration() {
        return Maxduration.get();
    }

    public SimpleIntegerProperty maxdurationProperty() {
        return Maxduration;
    }

    @Override
    public String toString() {
        return "Prufling{" +
                "PruflingID=" + PruflingID +
                ", serialNumber=" + serialNumber +
                ", Maxduration=" + Maxduration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prufling prufling = (Prufling) o;
        return PruflingID == prufling.PruflingID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PruflingID);
    }
}
