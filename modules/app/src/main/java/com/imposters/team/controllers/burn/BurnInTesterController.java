package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import com.imposters.team.controllers.context.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BurnInTesterController extends UpperAnchorPaneFunctionalities
        implements Initializable {
    
    @FXML
    private Label alertMessage;

    @FXML
    private TextField Artikelnummer;

    @FXML
    private TextField Auftragsnummer;

    @FXML
    public void clickWeiterBtn() {
        String Artikelnummer = this.Artikelnummer.getText();
        String Auftragsnummer = this.Auftragsnummer.getText();
        App.changeView("/fxml/burnIn-views/burnInTester2.fxml");
        alertMessage.setText("Was wollen Sie hier machen?");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setStatusBar(Context.getUser(),Context.getEnvChamber());
    }
}