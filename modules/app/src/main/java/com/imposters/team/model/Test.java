package com.imposters.team.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Objects;

public class Test {

    private final SimpleIntegerProperty slot_ID;
    private final Curve curve;
    private final UnitUnderTest prufling;
    private final SimpleBooleanProperty failurestatus;
    private final SimpleIntegerProperty takenduration;
    // constructors

    public Test(int slot_ID, Curve curve, UnitUnderTest prufling, boolean failurestatus, int takenduration) {
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

    public UnitUnderTest getPrufling() {
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
