package com.imposters.team.controllers.loginViews;

import com.imposters.team.App;
import com.imposters.team.controllers.CloseMinimizeFunctionalities;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ChamberSelectController extends CloseMinimizeFunctionalities {

    @FXML
    private Button chamberSelectingButton;

    @FXML
    private ComboBox<String> chamberComboBox;

    @FXML
    public void DropDownClicked(){
        chamberComboBox.getItems().setAll(
            "We need to connect that",
            "To the database and",
            "then get the names of ",
            "chambers from there"
        );
    }

    @FXML
    public void onChamberSelectingbtnClicked() {
        App.getToServerSender().toServer("INIT|"+chamberComboBox.getSelectionModel().getSelectedItem()+"|1012323");
        App.changeView("/burnInViews/burnInTester.fxml");
    }

}


