package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.controllers.context.Context;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;


public class BurnInTester2Controller extends UpperAnchorPaneFunctionalities implements Initializable{

    private List<String> preTestList = new ArrayList<>();


    @FXML
    private Label message;
    @FXML
    private AnchorPane clockAnchor;
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

        preTestList.add("STRTPRE|32");
        for (int i = 0; i <20; i++) {
            preTestList.add("PRETST|"+i);
        }
        preTestList.add("ENDPRE");
        preTestList.stream().forEach(item -> this.client.toServer(item));

//        new Thread(() ->
//                preTestList.stream().forEach(item -> this.client.toServer(item))
//            );
        
    }
}
