package com.imposters.team.controllers.burn;

import com.imposters.team.controllers.CloseMinimizeFunctionalities;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BurnInTesterController extends CloseMinimizeFunctionalities {
    
    @FXML
    private Label alertMessage;

    @FXML
    private TextField ArtikelnummerTextField;

    @FXML
    private TextField AuftragsnummerTextField;

    @FXML
    public void weiterBtnClicked() {
        String Artikelnummer = ArtikelnummerTextField.getText();
        String Auftragsnummer = AuftragsnummerTextField.getText();
        alertMessage.setText("Was wollen Sie hier machen?");
    }

}