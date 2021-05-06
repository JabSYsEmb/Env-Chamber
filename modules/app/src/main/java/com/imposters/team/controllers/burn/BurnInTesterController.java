package com.imposters.team.controllers.burn;

import com.imposters.team.App;
import com.imposters.team.controllers.CloseMinimizeFunctionalities;
import com.imposters.team.db.MyJDBC;
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
        MyJDBC db = App.getDatabase();
        alertMessage.setText("Was wollen Sie hier machen?");
    }

}