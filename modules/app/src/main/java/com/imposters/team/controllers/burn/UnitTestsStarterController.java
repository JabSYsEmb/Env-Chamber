package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.controllers.clock.ClockController;

import com.imposters.team.controllers.context.Context;
import com.imposters.team.dao.CurveDao;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.util.ResourceBundle;
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

    private volatile Thread temperatureUpdaterThread;

    @FXML
    private Button weiterBtn;

    private boolean testStatus = false;

    @FXML
    @Override
    public void nextClicked() {
        if(!testStatus){
            this.client.toServer("SETTARGET|70.5|19|3|5");
            this.run(this.Tempratur, 20);
        }
        else {
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
    }

    public void run(Label temperature, int seconds) {
        this.temperatureUpdaterThread = new Thread(() ->
        {
            try {
                this.updateTemperature(temperature,seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.temperatureUpdaterThread.start();
    }

    public void updateTemperature(Label temperature, int seconds) throws InterruptedException{
        for(int i = 0; i < seconds; ++i){
            Platform.runLater( () ->
                    temperature.setText(String.format("%.02f", this.client.opertempHandler())));
            Thread.sleep(1000);
        }
        this.changeTheStatusOfTheWeiterBtn();
        this.stopTheRunningThread();
    }

    public void changeTheStatusOfTheWeiterBtn(){
        Platform.runLater( () -> {
            this.testStatus = true;
            this.weiterBtn.setText("Weiter");
        });
    }

    public void stopTheRunningThread() {
        temperatureUpdaterThread = null;
    }

    public void printDefintionOfTheSelectedCurve(int curveID) {
        CurveDao.getCurvesFromDatabase(this.db).stream().forEach(item -> {
            for(int i = 1 ; i <= CurveDao.getCurveDefinitionsFromDatabase(curveID, this.db).size(); ++i) {
                System.out.println(
                        i +
                                ". Temperature : " +
                                CurveDao.getCurveDefinitionsFromDatabase(curveID, this.db).get(i).getTemperature() +
                                " Duration : " + CurveDao.getCurveDefinitionsFromDatabase(2, this.db).get(i).getDuration()
                );
            }
        });
    }

    public void setTargetTemperatureForTests(){

    }
}