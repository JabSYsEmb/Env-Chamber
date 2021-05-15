package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.clock.ClockController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.net.URL;


public class BurnInTester2Controller extends UpperAnchorPaneFunctionalities implements Initializable{

    @FXML
    private Label message;
    @FXML
    private AnchorPane clockAnchor;
    @FXML
    private Label clock;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ClockController(5,"Initialisierungsvorgang ist beendet!").run(clock,message);
    }

    @FXML
    @Override
    public void clickWeiterBtn(){
        App.changeView("/fxml/login/login.fxml");
    }
}