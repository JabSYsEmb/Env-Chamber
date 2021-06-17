package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class UnitUnderTest
{
    //Attribute in prufling
  /*public int Prufling_ID;
	public String Serialnumber;
	public int Maxduration;*/
    private int PruflingID;
    private SimpleStringProperty serialNumber;

    public UnitUnderTest(int UnitId, String serialNumber)
    {
        this.PruflingID = UnitId;
        this.serialNumber = new SimpleStringProperty(serialNumber);
    }

    public int getPruflingID() 
    {
        return PruflingID;
    }

    public String getSerialNumber() 
    {
        return serialNumber.get();
    }

    public SimpleStringProperty serialNumberProperty() 
    {
        return serialNumber;
    }

    @Override
    public String toString() 
    {
        return "Prufling{" +
                "PruflingID=" + PruflingID +
                ", serialNumber=" + serialNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitUnderTest prufling = (UnitUnderTest) o;
        return PruflingID == prufling.PruflingID;
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(PruflingID);
    }
}
