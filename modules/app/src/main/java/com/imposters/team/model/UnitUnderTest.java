package com.imposters.team.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class UnitUnderTest {

    private SimpleIntegerProperty PruflingID;
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty curveTaskNumber;
    private SimpleBooleanProperty status;


    public UnitUnderTest(int UnitId, String serialNumber, String curveTaskNumber, boolean status) {
        this.PruflingID = new SimpleIntegerProperty(UnitId);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.curveTaskNumber = new SimpleStringProperty(curveTaskNumber);
        this.status = new SimpleBooleanProperty(status);
    }

    public int getPruflingID() {
        return PruflingID.get();
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public SimpleStringProperty serialNumberProperty() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "UnitUnderTest{" +
                "PruflingID=" + PruflingID +
                ", serialNumber=" + serialNumber +
                ", curveTaskNumber=" + curveTaskNumber +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitUnderTest prufling = (UnitUnderTest) o;
        return PruflingID == prufling.PruflingID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PruflingID);
    }
}
