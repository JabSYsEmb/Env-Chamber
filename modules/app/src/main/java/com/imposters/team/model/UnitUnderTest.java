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
    private SimpleIntegerProperty slotId;
    // Constructor
    public UnitUnderTest(int slotId, String serialNumber, String curveTaskNumber, boolean status) {
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.slotId = new SimpleIntegerProperty(slotId);
        this.curveTaskNumber = new SimpleStringProperty(curveTaskNumber);
        this.status = new SimpleBooleanProperty(status);
    }

    public UnitUnderTest(int UnitId, int slotId, String serialNumber, String curveTaskNumber, boolean status) {
        this.PruflingID = new SimpleIntegerProperty(UnitId);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.slotId = new SimpleIntegerProperty(slotId);
        this.curveTaskNumber = new SimpleStringProperty(curveTaskNumber);
        this.status = new SimpleBooleanProperty(status);
    }

    public int getPruflingID() {
        return PruflingID.get();
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public String getCurveTaskNumber() {
        return curveTaskNumber.get();
    }

    public boolean isStatus() {
        return status.get();
    }

    public int getSlotId() {
        return slotId.get();
    }

    public SimpleStringProperty curveTaskNumberProperty() {
        return curveTaskNumber;
    }

    public SimpleIntegerProperty pruflingIDProperty() {
        return PruflingID;
    }

    public SimpleBooleanProperty statusProperty() {
        return status;
    }

    public SimpleIntegerProperty slotIdProperty() {
        return slotId;
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
