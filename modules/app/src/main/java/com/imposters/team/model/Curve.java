package com.imposters.team.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Curve {
    HashMap<Integer, CurveDefinition> CurveDefinitions = new HashMap<Integer, CurveDefinition>();
    private final int id;
    private final SimpleStringProperty taskNumber;
    // constructor

    public Curve(int id, String taskNumber, HashMap<Integer, CurveDefinition> curveDefinitions) {
        this.id = id;
        this.taskNumber = new SimpleStringProperty(taskNumber);
        CurveDefinitions = curveDefinitions;
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

    public HashMap<Integer, CurveDefinition> getCurveDefinitions() {
        return CurveDefinitions;
    }

    @Override
    public String toString() {
        return "Curve{" +
                "id=" + id +
                ", taskNumber=" + taskNumber +
                ", CurveDefinitions=" + CurveDefinitions +
                '}';
    }
}
