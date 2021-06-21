package com.imposters.team.controllers.context;

import com.imposters.team.model.*;

import java.util.List;

public class Context {
    private static UnitUnderTest unitUnderTest;
    private static List<String> setMsgToSent;
    private static EnvChamber envChamber;
    private static List<Test> tests;
    private static Report report;

    private static Curve curve;

    private static User user;
    public static List<String> getSetMsgToSent() {
        return setMsgToSent;
    }

    public static void setSetMsgToSent(List<String> setMsgToSent) {
        Context.setMsgToSent = setMsgToSent;
    }

    public static EnvChamber getEnvChamber() {
        return envChamber;
    }

    public static void setEnvChamber(EnvChamber envChamber) {
        Context.envChamber = envChamber;
    }

    public static UnitUnderTest getUnitUnderTest() {
        return unitUnderTest;
    }

    public static void setUnitUnderTest(UnitUnderTest unitUnderTest) {
        Context.unitUnderTest = unitUnderTest;
    }

    public static List<Test> getTests() {
        return tests;
    }

    public static void setTests(List<Test> tests) {
        Context.tests = tests;
    }

    public static Report getReport() {
        return report;
    }

    public static void setReport(Report report) {
        Context.report = report;
    }


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Context.user = user;
    }

    public static void setChamber(EnvChamber chamber) {
        Context.envChamber = chamber;
    }

    public static Curve getCurve() {
        return curve;
    }

    public static void setCurve(Curve curve) {
        Context.curve = curve;
    }
}
