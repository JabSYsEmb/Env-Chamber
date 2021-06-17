package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;

public class CurveDefinition 
{
    private SimpleIntegerProperty Temperature;
    private SimpleIntegerProperty duration;

    public CurveDefinition( int Temperature, int duration) 
    {
        this.Temperature = new SimpleIntegerProperty(Temperature);
        this.duration = new SimpleIntegerProperty(duration);
    }

    public int getTemperature() 
    {
        return Temperature.get();
    }

    public SimpleIntegerProperty temperatureProperty() 
    {
        return Temperature;
    }

    public int getDuration() 
    {
        return duration.get();
    }

    public SimpleIntegerProperty durationProperty() 
    {
        return duration;
    }

    @Override
    public String toString() 
    {
        return "Step{" +
                "Temperature=" + getTemperature() +
                ", duration=" + getDuration() +
                '}';
    }
}
