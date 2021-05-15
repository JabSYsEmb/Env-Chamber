package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;
import com.imposters.team.controllers.clock.ClockController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class BurnInTester4Controller extends UpperAnchorPaneFunctionalities implements Initializable{

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ClockController(5,"BurnInTester4Controller").run(clock,message);
    }

    @FXML
    @Override
    public void clickWeiterBtn(){

        //App.changeView("/fxml/burnIn-views/burnInTester5.fxml");
        message.setText("Hallo heißer ich bin Message");
        progressBar.setProgress(0.88);
        Tempratur.setText("0080.9");
    }
}