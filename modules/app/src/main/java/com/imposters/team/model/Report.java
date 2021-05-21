package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Report {
    //Attribute in Bericht
    /*public int Bericht_ID;
    public int User_ID;
    public int Envchamber_ID;
    public java.sql.Date Date*/
    // Wir brauchen hier eine Array for testobjekte

    private int ReportID;
    private User user;
    private EnvChamber envChamber;
    private SimpleDateFormat date;
    private List<Test> test = new ArrayList<>();

    public Report(int reportID, User user, EnvChamber envChamber, String date, List<Test> test) {
        ReportID = reportID;
        this.user = user;
        this.envChamber = envChamber;
        this.date = new SimpleDateFormat(date);// String achtung!
        this.test = test;
    }

    public int getReportID() {
        return ReportID;
    }

    public User getUser() {
        return user;
    }

    public EnvChamber getEnvChamber() {
        return envChamber;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public List<Test> getTest() {
        return test;
    }
    public synchronized boolean addTest(Test e){
        return test.add(e);
    }


    @Override
    public String toString() {
        return "Report{" +
                "ReportID=" + ReportID +
                ", user=" + user +
                ", envChamber=" + envChamber +
                ", date=" + date +
                ", test=" + test +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return ReportID == report.ReportID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ReportID);
    }
}