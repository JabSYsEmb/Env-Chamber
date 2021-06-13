package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;
import com.imposters.team.App;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.controllers.context.Context;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;


public class BurnInTester2Controller extends UpperAnchorPaneFunctionalities implements Initializable{

    private List<String> preTestList = new ArrayList<>();


    @FXML
    private Label message;
    @FXML
    private Label clock;

    @FXML
    @Override
    public void nextClicked(){
        App.changeView("/fxml/burnIn-views/burnInTester3.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ClockController(1,"Initialisierungsvorgang ist beendet!").run(clock,message);
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());

        new Thread( () ->
            this.runPreTest()
        ).start();

    }

    public void runPreTest(){
        this.preTestList.add("STRTPRE|32");
        for (int i = 1; i <= 20; i++) {
            this.preTestList.add("PRETST|"+i);
        }
        this.preTestList.add("ENDPRE");
        this.preTestList.stream().forEach(item -> this.client.toServer(item));
    }

}
