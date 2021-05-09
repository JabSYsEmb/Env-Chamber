package com.imposters.team.controllers.login;

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
            "Cabinet-1",
            "Cabinet-2",
            "Cabinet-3",
            "Cabinet-4",
            "..."
        );
    }

    @FXML
    public void onChamberSelectingbtnClicked() {
        App.getToServerSender().toServer("INIT|"+chamberComboBox.getSelectionModel().getSelectedItem()+"|1012323");
        App.changeView("/burnInViews/burnInTester.fxml");
    }

}


