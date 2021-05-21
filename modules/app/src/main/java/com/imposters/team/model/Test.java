package com.imposters.team.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.Objects;

public class Test {
    // Attribute in tset (es sollte als eine  Array im Bericht gespeisert werden)
    /*public int Slot_ID;
    public int Curve_ID;
    public int Bericht_ID; // das brauchen wir nicht weil wir liegen schon im Berichtclass
    public int Prufling_ID;
    public byte Failurestatus;
    public int Takenduration;
    public java.sql.Date Startingtime;*/
    private SimpleIntegerProperty slot_ID;
    private Curve curve;
    private Prufling prufling;
    private SimpleBooleanProperty failurestatus;
    private SimpleIntegerProperty takenduration;
    // constructors

    public Test(int slot_ID, Curve curve, Prufling prufling, boolean failurestatus, int takenduration) {
        this.slot_ID = new SimpleIntegerProperty(slot_ID);
        this.curve = curve;
        this.prufling = prufling;
        this.failurestatus = new SimpleBooleanProperty(failurestatus);
        this.takenduration = new SimpleIntegerProperty(takenduration);
    }

    public int getSlot_ID() {
        return slot_ID.get();
    }

    public SimpleIntegerProperty slot_IDProperty() {
        return slot_ID;
    }

    public Curve getCurve() {
        return curve;
    }

    public Prufling getPrufling() {
        return prufling;
    }

    public boolean isFailurestatus() {
        return failurestatus.get();
    }

    public SimpleBooleanProperty failurestatusProperty() {
        return failurestatus;
    }

    public int getTakenduration() {
        return takenduration.get();
    }

    public SimpleIntegerProperty takendurationProperty() {
        return takenduration;
    }

    @Override
    public String toString() {
        return "Test{" +
                "slot_ID=" + slot_ID +
                ", curve=" + curve +
                ", prufling=" + prufling +
                ", failurestatus=" + failurestatus +
                ", takenduration=" + takenduration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return slot_ID.equals(test.slot_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot_ID);
    }
}
