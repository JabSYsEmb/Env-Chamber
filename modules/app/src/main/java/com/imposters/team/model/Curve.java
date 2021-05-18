package com.imposters.team.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Curve {
    //Attribute in curve
  /*public int Curve_ID;
	public String Tasknumber;
	public String Duration;*/
    // wir brauchen hier eine array for Curvedurationen


    private int id;
    // ArrayList is not synchronized, which means multiple
    // threads can work on arrayList at the same time.
    private ArrayList<Integer> duration; // duration stored in unit of second
    private ArrayList<Integer> temperature;
    private SimpleStringProperty taskNumber;

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

    public ArrayList<Integer> getDuration() {
        return duration;
    }

    public int getWholeDurationInSeconds(){
        return this.getDuration().stream().mapToInt(obj->(int) obj).sum();
    }

    public ArrayList<Integer> getTemperature() {
        return temperature;
    }
}
