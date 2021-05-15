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
<<<<<<< HEAD
        new ClockController(1,"Initialisierungsvorgang ist beendet!").run(clock,message);
=======
        new ClockController(5,"Initialisierungsvorgang ist beendet!").run(clock,message);
>>>>>>> a76ed9624cf4d2dd29584211dd4ac7e3d1e46698
    }

    @FXML
    @Override
    public void clickWeiterBtn(){
        App.changeView("/fxml/burnIn-views/burnInTester3.fxml");
    }
}