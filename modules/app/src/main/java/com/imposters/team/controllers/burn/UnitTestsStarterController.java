package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.UpperAndLowerBarConfigurator;
import com.imposters.team.controllers.clock.ClockController;

import com.imposters.team.controllers.context.Context;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.net.URL;

public class UnitTestsStarterController extends UpperAndLowerBarConfigurator implements Initializable
{

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
    @Override
    public void nextClicked()
    {

        //App.changeView("/fxml/burnIn-views/ReportReview.fxml");
        message.setText("Hallo heißer ich bin Message");
        progressBar.setProgress(0.88);
        Tempratur.setText("0080.9");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        new ClockController(5,"BurnInTester4Controller").run(clock,message);
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());
    }
}