package com.imposters.team.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Curve {
    private int id;
    private SimpleStringProperty taskNumber;
    private List<ArrayList<Integer>> definition; // duration stored in unit of second

    // constructor

    public Curve(int id, List<ArrayList<Integer>> definition, SimpleStringProperty taskNumber) {
        this.id = id;
        this.definition = definition;
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

    public List<ArrayList<Integer>> getDefinition() {
        return definition;
    }
}
