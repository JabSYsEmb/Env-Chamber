package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.UpperAnchorPaneFunctionalities;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BurnInTesterController extends UpperAnchorPaneFunctionalities {
    
    @FXML
    private Label alertMessage;

    @FXML
    private TextField ArtikelnummerTextField;

    @FXML
    private TextField AuftragsnummerTextField;

    @FXML
    public void clickWeiterBtn() {
        String Artikelnummer = ArtikelnummerTextField.getText();
        String Auftragsnummer = AuftragsnummerTextField.getText();
        App.changeView("/fxml/burnIn-views/burnInTester2.fxml");
        alertMessage.setText("Was wollen Sie hier machen?");
    }

}