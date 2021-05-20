package com.imposters.team.model;

import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class Curve {
    private int id;
    private List<Curveduration> duration; // duration stored in unit of second
    private SimpleStringProperty taskNumber;

    // constructor

    public Curve(int id, List<Curveduration> duration, SimpleStringProperty taskNumber) {
        this.id = id;
        this.duration = duration;
        this.taskNumber = taskNumber;
    }

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

    public List<Curveduration> getDuration() {
        return duration;
    }
}
