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




    private volatile Thread temperatureUpdaterThread;
    private List<CurveDefinition> curveDefinitionList = new ArrayList<>();
    private boolean testStatus = false;
    private int i = 0;

    @FXML
    @Override
    public void nextClicked() {
        if(!testStatus){
            this.run();
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
        this.setTemperatureThread();
    }

    public void setTemperatureThread() {
        this.temperatureUpdaterThread = this.setMessageToServerForStartTesting(
                this.getCurveDefinitionForTest(Context.getCurve().getId(),this.db),
                this.Tempratur,
                this.sollTempratur);
        ++i;
    }
    public void run(){
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

    public List<CurveDefinition> getCurveDefinitionForTest(int curveId, MyJDBC db){
        List<CurveDefinition> curveDefinitionList = new ArrayList<>();
        HashMap<Integer, CurveDefinition> tempHashTable = CurveDao.getCurveDefinitionsFromDatabase(curveId,db);
        for(int i =1 ;i <= tempHashTable.size(); ++i) {
            curveDefinitionList.add(tempHashTable.get(i));
        }
        return curveDefinitionList;
    }

    public Thread setMessageToServerForStartTesting(List<CurveDefinition> list, Label tempratur, Label sollTempratur) {
        return new Thread( () ->
        {
            System.out.println(list.get(i));
            this.client.toServer("SETTARGET|" +
                    list.get(i).getTemperature() + "|" +
                    list.get(i).getDuration() + "|" +
                    "3|5");
            Platform.runLater(()->this.sollTempratur.setText(String.format("Soll Tempratur : %d.0",list.get(i).getTemperature())));
            try {
                this.updateTemperature(tempratur, (list.get(i).getDuration() * 60));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
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