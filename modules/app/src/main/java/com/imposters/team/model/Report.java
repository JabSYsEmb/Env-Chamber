package com.imposters.team.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Report {

    private int ReportID;
    private final User user;
    private final EnvChamber envChamber;
    private final LocalDate date;
    private List<Test> test = new ArrayList<>();

    public Report(User user, EnvChamber envChamber, LocalDate date, List<Test> test) {
        this.ReportID = (int) Math.floor(Math.random()*(20000-10+1)+2000);
        this.user = user;
        this.envChamber = envChamber;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public List<Test> getTest() {
        return test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return ReportID == report.ReportID;
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
    public int hashCode() {
        return Objects.hash(ReportID);
    }
}
