package com.imposters.team.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.Vector;

public class Curve {
    private int id;
    private SimpleStringProperty taskNumber;
    private Vector<Integer> Duration;
    private Vector<Integer> Temperature;

    public int getId() {
        return id;
    }

    public String getTaskNumber() {
        return taskNumber.get();
    }

    public SimpleStringProperty taskNumberProperty() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber.set(taskNumber);
    }

    public Vector<Integer> getDuration() {
        return Duration;
    }

    public int getWholeDurationInSeconds(){
        return this.getDuration().stream().mapToInt(obj->(int) obj).sum();
    }

    public Vector<Integer> getTemperature() {
        return Temperature;
    }
}
