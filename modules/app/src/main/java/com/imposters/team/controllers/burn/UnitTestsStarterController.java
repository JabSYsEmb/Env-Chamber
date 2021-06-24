package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.controllers.clock.ClockController;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.CurveDao;
import com.imposters.team.db.MyJDBC;
import com.imposters.team.model.CurveDefinition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.*;
import java.net.URL;

public class UnitTestsStarterController extends MainConfigurations implements Initializable {

    @FXML
    private Label message;
    @FXML
    private Label clock;
    @FXML
    private Label Testdauer;
    @FXML
    private Label Tempratur;
    @FXML
    private Label sollTempratur;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button weiterBtn;


    private ClockController clockController;
    private volatile Thread temperatureUpdaterThread;
    private List<CurveDefinition> curveDefinitionList = new ArrayList<>();
    private volatile boolean isTestDone = false;

    @FXML
    @Override
    public void nextClicked() {
        if (!isTestDone) {
            this.run();
            this.weiterBtn.setDisable(true);
        } else {
            App.changeView("/fxml/burnIn-views/UnitTestsPinger.fxml");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.weiterBtn.setText("Testen");
        new ClockController(5, "UnitTestsStarterController").run(clock, message);
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
        // Sending BurnIn Message to the server
        this.client.toServer("STRTBURNIN");
        this.setTemperatureThread();
    }

    public void setTemperatureThread() {
        this.temperatureUpdaterThread = new Thread (() -> this.setMessageToServerForStartTesting(
                this.getCurveDefinitionForTest(Context.getCurve().getId(), this.db),
                this.Tempratur,
                this.sollTempratur));
    }

    public void run() {
        this.temperatureUpdaterThread.start();
    }

    public void updateTemperature(Label temperature, int seconds) throws InterruptedException {
        for (int i = 0; i <= seconds; ++i) {
            this.setTextOfLabel(temperature);
        }
        // wait for 3 seconds after each iteration
        Thread.sleep(3000);
    }

    public void setTextOfLabel(Label temperature) throws InterruptedException {
        Platform.runLater(() ->
                temperature.setText(String.format("%.02f", this.client.opertempHandler())));
        Thread.sleep(1000);
    }

    public void stopTheRunningThread() {
        temperatureUpdaterThread = null;
    }

    public List<CurveDefinition> getCurveDefinitionForTest(int curveId, MyJDBC db) {
        List<CurveDefinition> curveDefinitionList = new ArrayList<>();
        HashMap<Integer, CurveDefinition> tempHashTable = CurveDao.getCurveDefinitionsFromDatabase(curveId, db);
        for (int i = 1; i <= tempHashTable.size(); ++i) {
            curveDefinitionList.add(tempHashTable.get(i));
        }
        return curveDefinitionList;
    }

    public void setMessageToServerForStartTesting(List<CurveDefinition> list, Label tempratur, Label sollTempratur) {
        list.forEach(item -> {
            this.client.toServer("SETTARGET|" +
                    item.getTemperature() + "|" +
                    item.getDuration() + "|" +
                    "3|5");

            this.setTargetTemperatureLabel(item.getTemperature());

            try {
                this.updateTemperature(tempratur, (item.getDuration()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.changeTheStatusOfTheWeiterBtn();
    }

    public void changeTheStatusOfTheWeiterBtn() {
        Platform.runLater(() -> {
            this.isTestDone = true;
            this.weiterBtn.setDisable(false);
            this.weiterBtn.setText("Weiter");
        });
    }

    public void setTargetTemperatureLabel(int targetTemperature){
        Platform.runLater(() -> {
            this.sollTempratur.setText(String.format("Soll Tempratur : %d.0", targetTemperature));
        });
    }
}