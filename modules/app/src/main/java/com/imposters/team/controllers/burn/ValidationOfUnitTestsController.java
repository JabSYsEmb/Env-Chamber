package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.stream.Collectors;


public class ValidationOfUnitTestsController extends MainConfigurations implements Initializable {

    private final List<String> preTestList = new ArrayList<>();
    private boolean status = true;

    @FXML
    private Label message;
    @FXML
    private Label clock;
    @FXML
    private Button weiterBtn;

    private ClockController timestamp;

    @FXML
    @Override
    public void nextClicked() {
        if(status) {
            timestamp.stopStopwatch();
            new Thread(() -> this.runPreTest()).start();
            this.weiterBtn.setText("Weiter");
            status = false;
        }else {
            App.changeView("/fxml/burnIn-views/UnitTestsPinger.fxml");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UnitTestsInitializationController.addedTestingUnits
                .stream()
                .filter((UnitUnderTest unitUnderTest) -> unitUnderTest != null)
                .collect(Collectors.toList())
                .stream()
                .forEach(item -> System.out.println(item));

        // Ending The initialization
        this.client.toServer("ENDINIT");

        timestamp = new ClockController(1, "Initialisierungsvorgang ist beendet!");
        timestamp.run(clock,message);
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());
    }

    public void runPreTest() {
        this.preTestList.add("STRTPRE|32");
        for (int i = 1; i <= 20; i++) {
            this.preTestList.add("PRETST|" + i);
        }
        this.preTestList.add("ENDPRE");
        this.preTestList.stream().forEach(item -> this.client.toServer(item));
    }

}
