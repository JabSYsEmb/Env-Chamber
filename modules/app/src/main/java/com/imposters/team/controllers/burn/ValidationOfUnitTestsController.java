package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.MainConfigurations;
import com.imposters.team.App;

import com.imposters.team.controllers.clock.ClockController;
import com.imposters.team.controllers.context.Context;
import com.imposters.team.model.UnitUnderTest;
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


    @FXML
    private Label message;
    @FXML
    private Label clock;

    @FXML
    @Override
    public void nextClicked() {
        App.changeView("/fxml/burnIn-views/UnitTestsPinger.fxml");
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

        new ClockController(1, "Initialisierungsvorgang ist beendet!").run(clock, message);
        this.setStatusBar(Context.getUser(), Context.getEnvChamber());

        new Thread(() -> this.runPreTest()).start();
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
